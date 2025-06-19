package com.lureclub.points.controller.user;

import com.lureclub.points.api.user.UserPointsApi;
import com.lureclub.points.entity.points.vo.response.PointsVo;
import com.lureclub.points.entity.points.vo.response.PointsHistoryVo;
import com.lureclub.points.entity.common.ApiResponse;
import com.lureclub.points.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户积分控制器实现
 *
 * @author system
 * @date 2025-06-19
 */
@RestController
public class UserPointsController implements UserPointsApi {

    @Autowired
    private PointsService pointsService;

    /**
     * 获取用户积分信息实现
     */
    @Override
    public ApiResponse<PointsVo> getUserPoints() {
        try {
            PointsVo result = pointsService.getUserPoints(null);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("获取积分信息失败: " + e.getMessage());
        }
    }

    /**
     * 获取用户积分历史记录实现
     */
    @Override
    public ApiResponse<List<PointsHistoryVo>> getUserPointsHistory(Long userId) {
        try {
            List<PointsHistoryVo> result = pointsService.getUserPointsHistory(userId);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("获取积分历史失败: " + e.getMessage());
        }
    }

    /**
     * 检查是否可抵扣指定积分实现
     */
    @Override
    public ApiResponse<Boolean> checkDeductPoints(Long userId, Integer points) {
        try {
            Boolean result = pointsService.checkDeductPoints(userId, points);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("检查抵扣失败: " + e.getMessage());
        }
    }

}