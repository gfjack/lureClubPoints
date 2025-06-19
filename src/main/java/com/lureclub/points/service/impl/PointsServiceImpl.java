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
import com.lureclub.points.util.PointsCalculatorUtil;
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
 * 积分服务实现类
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
    private PointsCalculatorUtil pointsCalculatorUtil;

    @Autowired
    private PointsConverter pointsConverter;

    @Autowired
    private ValidationUtil validationUtil;

    /**
     * 获取用户积分信息实现
     */
    @Override
    public PointsVo getUserPoints(Long userId) {
        Long targetUserId = userId != null ? userId : getCurrentUserId();

        // 确保用户存在
        User user = userRepository.findActiveUserById(targetUserId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("用户不存在");
        }

        // 获取或初始化积分记录
        Points points = pointsRepository.findByUserId(targetUserId);
        if (points == null) {
            initUserPoints(targetUserId);
            points = pointsRepository.findByUserId(targetUserId);
        }

        // 处理日期变更（将前一日积分转为有效积分）
        processDateChange(points);

        // 计算有效积分和总积分
        Integer effectivePoints = calculateEffectivePoints(targetUserId);
        Integer totalPoints = calculateTotalPoints(targetUserId);

        return pointsConverter.toPointsVo(points, effectivePoints, totalPoints);
    }

    /**
     * 获取用户积分历史实现
     */
    @Override
    public List<PointsHistoryVo> getUserPointsHistory(Long userId) {
        Long targetUserId = userId != null ? userId : getCurrentUserId();

        // 确保用户存在
        User user = userRepository.findActiveUserById(targetUserId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("用户不存在");
        }

        List<PointsHistory> historyList = pointsHistoryRepository
                .findByUserIdOrderByOperationDateDescCreateTimeDesc(targetUserId);

        return historyList.stream()
                .map(pointsConverter::toPointsHistoryVo)
                .collect(Collectors.toList());
    }

    /**
     * 录入用户积分实现（管理员操作）
     */
    @Override
    @Transactional
    public PointsVo addUserPoints(PointsAddVo pointsAddVo) {
        // 验证积分数量
        if (!validationUtil.isValidPoints(pointsAddVo.getPoints())) {
            throw new BusinessException("积分数量必须在1-10000之间");
        }

        // 确保用户存在
        User user = userRepository.findActiveUserById(pointsAddVo.getUserId()).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("用户不存在");
        }

        // 获取或初始化积分记录
        Points points = pointsRepository.findByUserId(pointsAddVo.getUserId());
        if (points == null) {
            initUserPoints(pointsAddVo.getUserId());
            points = pointsRepository.findByUserId(pointsAddVo.getUserId());
        }

        // 处理日期变更
        processDateChange(points);

        // 录入当日积分
        points.addTodayPoints(pointsAddVo.getPoints());
        pointsRepository.save(points);

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

    /**
     * 抵扣用户积分实现（管理员操作）
     */
    @Override
    @Transactional
    public PointsVo deductUserPoints(PointsDeductVo pointsDeductVo) {
        Long userId = pointsDeductVo.getUserId();
        Integer deductPoints = pointsDeductVo.getPoints();

        // 验证积分数量
        if (!validationUtil.isValidPoints(deductPoints)) {
            throw new BusinessException("积分数量必须在1-10000之间");
        }

        // 确保用户存在
        User user = userRepository.findActiveUserById(userId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("用户不存在");
        }

        // 获取积分记录
        Points points = pointsRepository.findByUserId(userId);
        if (points == null) {
            throw new BusinessException("用户积分记录不存在");
        }

        // 处理日期变更
        processDateChange(points);

        // 检查有效积分是否足够
        Integer effectivePoints = calculateEffectivePoints(userId);
        if (effectivePoints < deductPoints) {
            throw new InsufficientPointsException(
                    String.format("有效积分不足，当前有效积分: %d，需要抵扣: %d", effectivePoints, deductPoints)
            );
        }

        // 记录积分历史（抵扣记录）
        PointsHistory history = new PointsHistory(
                userId,
                PointsType.DEDUCTED,
                -deductPoints, // 负数表示抵扣
                "管理员抵扣积分: " + (pointsDeductVo.getRemark() != null ? pointsDeductVo.getRemark() : "门票抵扣")
        );
        pointsHistoryRepository.save(history);

        return getUserPoints(userId);
    }

    /**
     * 检查是否可抵扣指定积分实现
     */
    @Override
    public Boolean checkDeductPoints(Long userId, Integer points) {
        if (!validationUtil.isValidPoints(points)) {
            return false;
        }

        try {
            Integer effectivePoints = calculateEffectivePoints(userId);
            return effectivePoints >= points;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取用户有效积分实现
     */
    @Override
    public Integer getUserEffectivePoints(Long userId) {
        return calculateEffectivePoints(userId);
    }

    /**
     * 获取用户总积分实现
     */
    @Override
    public Integer getUserTotalPoints(Long userId) {
        return calculateTotalPoints(userId);
    }

    /**
     * 初始化用户积分记录实现
     */
    @Override
    @Transactional
    public void initUserPoints(Long userId) {
        Points existingPoints = pointsRepository.findByUserId(userId);
        if (existingPoints == null) {
            Points points = new Points(userId);
            pointsRepository.save(points);
        }
    }

    /**
     * 获取所有用户积分数据实现（管理员）
     */
    @Override
    public List<PointsVo> getAllUserPoints() {
        List<User> allUsers = userRepository.findAllActiveUsers();

        return allUsers.stream()
                .map(user -> {
                    // 获取或初始化积分记录
                    Points points = pointsRepository.findByUserId(user.getId());
                    if (points == null) {
                        initUserPoints(user.getId());
                        points = pointsRepository.findByUserId(user.getId());
                    }

                    // 处理日期变更
                    processDateChange(points);

                    // 计算有效积分和总积分
                    Integer effectivePoints = calculateEffectivePoints(user.getId());
                    Integer totalPoints = calculateTotalPoints(user.getId());

                    return pointsConverter.toPointsVo(points, effectivePoints, totalPoints);
                })
                .collect(Collectors.toList());
    }

    /**
     * 处理日期变更逻辑
     */
    private void processDateChange(Points points) {
        LocalDate today = LocalDate.now();

        // 如果最后更新日期不是今天，则处理日期变更
        if (points.getLastPointsDate() == null || !points.getLastPointsDate().equals(today)) {
            points.processDateChange();
            pointsRepository.save(points);
        }
    }

// 在PointsServiceImpl中修复方法调用

    /**
     * 计算用户有效积分
     * 有效积分 = 历史所有获得积分 - 历史所有抵扣积分 - 当日积分
     */
    private Integer calculateEffectivePoints(Long userId) {
        LocalDate today = LocalDate.now();

        // 修改：使用正确的方法名
        List<PointsHistory> histories = pointsHistoryRepository.findByUserIdAndDateRangeOptional(
                userId, null, today.minusDays(1)
        );

        int effectivePoints = 0;
        for (PointsHistory history : histories) {
            if (history.getPointsType() == PointsType.EARNED) {
                effectivePoints += history.getPoints();
            } else if (history.getPointsType() == PointsType.DEDUCTED) {
                effectivePoints += history.getPoints(); // 这里已经是负数
            }
        }

        return Math.max(0, effectivePoints);
    }

    /**
     * 计算用户总积分
     * 总积分 = 历史所有获得积分的总和（不包括抵扣）
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
            // 如果是负数，说明是管理员，抛出异常
            if (id < 0) {
                throw new BusinessException("管理员无法查看个人积分");
            }
            return id;
        }
        throw new BusinessException("用户未登录");
    }

}