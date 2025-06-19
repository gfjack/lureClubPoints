package com.lureclub.points.util;

import com.lureclub.points.repository.PointsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * 积分计算工具类
 *
 * @author system
 * @date 2025-06-19
 */
@Component
public class PointsCalculatorUtil {

    @Autowired
    private PointsHistoryRepository pointsHistoryRepository;

    /**
     * 计算用户有效积分
     * 有效积分 = 历史获得积分 - 历史抵扣积分（不包括当日积分）
     *
     * @param userId 用户ID
     * @return 有效积分数量
     */
    public Integer calculateEffectivePoints(Long userId) {
        if (userId == null) {
            return 0;
        }

        LocalDate today = LocalDate.now();
        Integer effectivePoints = pointsHistoryRepository.calculateEffectivePoints(userId, today);
        return effectivePoints != null ? effectivePoints : 0;
    }

    /**
     * 计算用户总积分
     * 总积分 = 所有获得积分的总和
     *
     * @param userId 用户ID
     * @return 总积分数量
     */
    public Integer calculateTotalPoints(Long userId) {
        if (userId == null) {
            return 0;
        }

        Integer totalPoints = pointsHistoryRepository.calculateTotalPoints(userId);
        return totalPoints != null ? totalPoints : 0;
    }

    /**
     * 验证积分数量是否有效
     *
     * @param points 积分数量
     * @return 是否有效
     */
    public boolean isValidPoints(Integer points) {
        return points != null && points > 0;
    }

    /**
     * 验证抵扣积分是否有效
     *
     * @param points 抵扣积分数量
     * @param effectivePoints 当前有效积分
     * @return 是否可以抵扣
     */
    public boolean canDeduct(Integer points, Integer effectivePoints) {
        return isValidPoints(points) &&
                effectivePoints != null &&
                effectivePoints >= points;
    }

}