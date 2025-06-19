package com.lureclub.points.service.impl;

import com.lureclub.points.entity.points.Points;
import com.lureclub.points.entity.points.PointsHistory;
import com.lureclub.points.entity.points.vo.request.PointsAddVo;
import com.lureclub.points.entity.points.vo.request.PointsDeductVo;
import com.lureclub.points.entity.points.vo.response.PointsVo;
import com.lureclub.points.entity.points.vo.response.PointsHistoryVo;
import com.lureclub.points.entity.user.User;
import com.lureclub.points.enums.PointsType;
import com.lureclub.points.exception.BusinessException;
import com.lureclub.points.exception.InsufficientPointsException;
import com.lureclub.points.exception.UserNotFoundException;
import com.lureclub.points.repository.PointsRepository;
import com.lureclub.points.repository.PointsHistoryRepository;
import com.lureclub.points.repository.UserRepository;
import com.lureclub.points.service.PointsService;
import com.lureclub.points.util.ValidationUtil;
import com.lureclub.points.entity.points.PointsConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 积分服务实现类（最终修复版）
 *
 * @author system
 * @date 2025-06-19
 */
@Service
public class PointsServiceImpl implements PointsService {

    @Autowired
    private PointsRepository pointsRepository;

    @Autowired
    private PointsHistoryRepository pointsHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointsConverter pointsConverter;

    @Autowired
    private ValidationUtil validationUtil;

    @Override
    public PointsVo getUserPoints(Long userId) {
        Long targetUserId = userId != null ? userId : getCurrentUserId();

        // 修复：使用正确的Repository方法
        User user = userRepository.findById(targetUserId).orElse(null);
        if (user == null || user.getIsDeleted() == 1) {
            throw new UserNotFoundException("用户不存在");
        }

        Points points = pointsRepository.findByUserId(targetUserId);
        if (points == null) {
            initUserPoints(targetUserId);
            points = pointsRepository.findByUserId(targetUserId);
        }

        // 处理日期变更
        processDateChangeIfNeeded(points);

        // 计算总积分
        Integer totalPoints = calculateTotalPoints(targetUserId);

        return pointsConverter.toPointsVo(points, points.getEffectivePoints(), totalPoints);
    }

    @Override
    public List<PointsHistoryVo> getUserPointsHistory(Long userId) {
        Long targetUserId = userId != null ? userId : getCurrentUserId();

        User user = userRepository.findById(targetUserId).orElse(null);
        if (user == null || user.getIsDeleted() == 1) {
            throw new UserNotFoundException("用户不存在");
        }

        List<PointsHistory> historyList = pointsHistoryRepository
                .findByUserIdOrderByOperationDateDescCreateTimeDesc(targetUserId);

        return historyList.stream()
                .map(pointsConverter::toPointsHistoryVo)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PointsVo addUserPoints(PointsAddVo pointsAddVo) {
        // 验证参数
        if (!validationUtil.isValidPoints(pointsAddVo.getPoints())) {
            throw new BusinessException("积分数量必须在1-10000之间");
        }

        // 验证用户存在
        User user = userRepository.findById(pointsAddVo.getUserId()).orElse(null);
        if (user == null || user.getIsDeleted() == 1) {
            throw new UserNotFoundException("用户不存在");
        }

        // 获取或初始化积分记录
        Points points = pointsRepository.findByUserId(pointsAddVo.getUserId());
        if (points == null) {
            initUserPoints(pointsAddVo.getUserId());
            points = pointsRepository.findByUserId(pointsAddVo.getUserId());
        }

        // 处理日期变更
        processDateChangeIfNeeded(points);

        // 录入当日积分
        points.addTodayPoints(pointsAddVo.getPoints());
        points = pointsRepository.save(points);

        // 记录积分历史
        PointsHistory history = new PointsHistory(
                pointsAddVo.getUserId(),
                PointsType.EARNED,
                pointsAddVo.getPoints(),
                "管理员录入积分: " + (pointsAddVo.getRemark() != null ? pointsAddVo.getRemark() : "当日钓鱼获得积分")
        );
        pointsHistoryRepository.save(history);

        return getUserPoints(pointsAddVo.getUserId());
    }

    @Override
    @Transactional
    public PointsVo deductUserPoints(PointsDeductVo pointsDeductVo) {
        Long userId = pointsDeductVo.getUserId();
        Integer deductPoints = pointsDeductVo.getPoints();

        // 验证参数
        if (!validationUtil.isValidPoints(deductPoints)) {
            throw new BusinessException("积分数量必须在1-10000之间");
        }

        // 验证用户存在
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || user.getIsDeleted() == 1) {
            throw new UserNotFoundException("用户不存在");
        }

        // 获取积分记录
        Points points = pointsRepository.findByUserId(userId);
        if (points == null) {
            throw new BusinessException("用户积分记录不存在");
        }

        // 处理日期变更
        processDateChangeIfNeeded(points);

        // 检查是否可以抵扣
        if (!points.canDeduct(deductPoints)) {
            throw new InsufficientPointsException(
                    String.format("有效积分不足，当前有效积分: %d，需要抵扣: %d",
                            points.getEffectivePoints(), deductPoints)
            );
        }

        // 执行抵扣
        points.deductEffectivePoints(deductPoints);
        points = pointsRepository.save(points);

        // 记录抵扣历史
        PointsHistory history = new PointsHistory(
                userId,
                PointsType.DEDUCTED,
                -deductPoints, // 负数表示抵扣
                "管理员抵扣积分: " + (pointsDeductVo.getRemark() != null ? pointsDeductVo.getRemark() : "门票抵扣")
        );
        pointsHistoryRepository.save(history);

        return getUserPoints(userId);
    }

    @Override
    public Boolean checkDeductPoints(Long userId, Integer points) {
        if (!validationUtil.isValidPoints(points)) {
            return false;
        }

        try {
            Points userPoints = pointsRepository.findByUserId(userId);
            if (userPoints == null) {
                return false;
            }

            processDateChangeIfNeeded(userPoints);
            return userPoints.canDeduct(points);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Integer getUserEffectivePoints(Long userId) {
        Points points = pointsRepository.findByUserId(userId);
        if (points == null) {
            return 0;
        }

        processDateChangeIfNeeded(points);
        return points.getEffectivePoints();
    }

    @Override
    public Integer getUserTotalPoints(Long userId) {
        return calculateTotalPoints(userId);
    }

    @Override
    @Transactional
    public void initUserPoints(Long userId) {
        Points existingPoints = pointsRepository.findByUserId(userId);
        if (existingPoints == null) {
            Points points = new Points(userId);
            pointsRepository.save(points);
        }
    }

    @Override
    public List<PointsVo> getAllUserPoints() {
        // 修复：直接查询所有活跃用户
        List<User> allUsers = userRepository.findAll().stream()
                .filter(user -> user.getIsDeleted() == 0)
                .collect(Collectors.toList());

        return allUsers.stream()
                .map(user -> {
                    try {
                        return getUserPoints(user.getId());
                    } catch (Exception e) {
                        // 记录错误但不影响其他用户
                        return null;
                    }
                })
                .filter(pointsVo -> pointsVo != null)
                .collect(Collectors.toList());
    }

    /**
     * 处理日期变更
     */
    @Transactional
    public void processDateChangeIfNeeded(Points points) {
        if (points.processDateChange()) {
            pointsRepository.save(points);
        }
    }

    /**
     * 批量处理日期变更（供定时任务使用）
     */
    @Transactional
    public int batchProcessDateChange() {
        LocalDate today = LocalDate.now();
        List<Points> pointsNeedProcessing = pointsRepository.findPointsNeedDateProcessing(today);

        int processedCount = 0;
        for (Points points : pointsNeedProcessing) {
            try {
                if (points.processDateChange()) {
                    pointsRepository.save(points);
                    processedCount++;
                }
            } catch (Exception e) {
                // 记录错误但继续处理其他记录
            }
        }

        return processedCount;
    }

    /**
     * 计算用户总积分
     */
    private Integer calculateTotalPoints(Long userId) {
        Integer totalPoints = pointsHistoryRepository.calculateTotalPoints(userId);
        return totalPoints != null ? totalPoints : 0;
    }

    /**
     * 获取当前登录用户ID
     */
    private Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Long) {
            Long id = (Long) principal;
            if (id < 0) {
                throw new BusinessException("管理员无法查看个人积分");
            }
            return id;
        }
        throw new BusinessException("用户未登录");
    }
}