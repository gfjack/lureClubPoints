package com.lureclub.points.entity.points;

import com.lureclub.points.entity.points.vo.response.PointsVo;
import com.lureclub.points.entity.points.vo.response.PointsHistoryVo;
import org.springframework.stereotype.Component;

/**
 * 积分转换器
 *
 * @author system
 * @date 2025-06-19
 */
@Component
public class PointsConverter {

    /**
     * 将Points实体转换为PointsVo
     *
     * @param points 积分实体
     * @param effectivePoints 有效积分
     * @param totalPoints 总积分
     * @return 积分VO
     */
    public PointsVo toPointsVo(Points points, Integer effectivePoints, Integer totalPoints) {
        if (points == null) {
            return null;
        }
        return new PointsVo(
                points.getUserId(),
                points.getTodayPoints(),
                effectivePoints != null ? effectivePoints : 0,
                totalPoints != null ? totalPoints : 0,
                points.getLastPointsDate()
        );
    }

    /**
     * 将PointsHistory实体转换为PointsHistoryVo
     *
     * @param history 积分历史实体
     * @return 积分历史VO
     */
    public PointsHistoryVo toPointsHistoryVo(PointsHistory history) {
        if (history == null) {
            return null;
        }
        return new PointsHistoryVo(
                history.getId(),
                history.getUserId(),
                history.getPointsType(),
                history.getPoints(),
                history.getOperationDate(),
                history.getRemark(),
                history.getCreateTime()
        );
    }

}