package com.lureclub.points.service;

import com.lureclub.points.entity.points.vo.request.PointsAddVo;
import com.lureclub.points.entity.points.vo.request.PointsDeductVo;
import com.lureclub.points.entity.points.vo.response.PointsVo;
import com.lureclub.points.entity.points.vo.response.PointsHistoryVo;

import java.util.List;

/**
 * 积分服务接口
 *
 * @author system
 * @date 2025-06-19
 */
public interface PointsService {

    /**
     * 获取用户积分信息
     *
     * @param userId 用户ID（为null时获取当前用户）
     * @return 积分信息
     */
    PointsVo getUserPoints(Long userId);

    /**
     * 获取用户积分历史
     *
     * @param userId 用户ID（为null时获取当前用户）
     * @return 积分历史列表
     */
    List<PointsHistoryVo> getUserPointsHistory(Long userId);

    /**
     * 录入用户积分（管理员操作）
     *
     * @param pointsAddVo 积分录入信息
     * @return 操作结果
     */
    PointsVo addUserPoints(PointsAddVo pointsAddVo);

    /**
     * 抵扣用户积分（管理员操作）
     *
     * @param pointsDeductVo 积分抵扣信息
     * @return 操作结果
     */
    PointsVo deductUserPoints(PointsDeductVo pointsDeductVo);

    /**
     * 检查是否可抵扣指定积分
     *
     * @param userId 用户ID
     * @param points 要抵扣的积分数量
     * @return 是否可抵扣
     */
    Boolean checkDeductPoints(Long userId, Integer points);

    /**
     * 获取用户有效积分
     *
     * @param userId 用户ID
     * @return 有效积分数量
     */
    Integer getUserEffectivePoints(Long userId);

    /**
     * 获取用户总积分
     *
     * @param userId 用户ID
     * @return 总积分数量
     */
    Integer getUserTotalPoints(Long userId);

    /**
     * 初始化用户积分记录
     *
     * @param userId 用户ID
     */
    void initUserPoints(Long userId);

    /**
     * 获取所有用户积分数据（管理员）
     *
     * @return 所有用户积分列表
     */
    List<PointsVo> getAllUserPoints();

}