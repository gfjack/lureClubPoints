package com.lureclub.points.api.user;

import com.lureclub.points.entity.points.vo.response.PointsVo;
import com.lureclub.points.entity.points.vo.response.PointsHistoryVo;
import com.lureclub.points.entity.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户积分API接口
 *
 * @author system
 * @date 2025-06-19
 */
@Tag(name = "用户积分接口", description = "用户查看积分相关接口")
@RequestMapping("/api/user/points")
public interface UserPointsApi {

    /**
     * 获取用户积分信息
     *
     * @return 用户积分信息（包含当日积分字段）
     */
    @Operation(summary = "获取用户积分", description = "获取当前用户的积分信息，包含有效积分和总积分")
    @GetMapping("")
    ApiResponse<PointsVo> getUserPoints();

    /**
     * 获取用户积分历史记录
     *
     * @param userId 用户ID（可选，不传则查询当前用户）
     * @return 积分历史记录列表
     */
    @Operation(summary = "获取积分历史", description = "获取用户的详细积分历史记录")
    @GetMapping("/history")
    ApiResponse<List<PointsHistoryVo>> getUserPointsHistory(
            @Parameter(description = "用户ID（可选）") @RequestParam(required = false) Long userId);

    /**
     * 检查是否可抵扣指定积分
     *
     * @param userId 用户ID
     * @param points 要抵扣的积分数量
     * @return 是否可抵扣
     */
    @Operation(summary = "检查积分抵扣", description = "检查用户是否有足够的有效积分进行抵扣")
    @GetMapping("/check-deduct")
    ApiResponse<Boolean> checkDeductPoints(
            @Parameter(description = "用户ID") @RequestParam Long userId,
            @Parameter(description = "抵扣积分数量") @RequestParam Integer points);

}