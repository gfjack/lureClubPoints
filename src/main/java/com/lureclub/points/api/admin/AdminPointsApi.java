package com.lureclub.points.api.admin;

import com.lureclub.points.entity.points.vo.request.PointsAddVo;
import com.lureclub.points.entity.points.vo.request.PointsDeductVo;
import com.lureclub.points.entity.points.vo.response.PointsVo;
import com.lureclub.points.entity.points.vo.response.PointsHistoryVo;
import com.lureclub.points.entity.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 管理员积分管理API接口
 *
 * @author system
 * @date 2025-06-19
 */
@Tag(name = "管理员积分管理接口", description = "管理员积分管理相关接口")
@RequestMapping("/api/admin/points")
public interface AdminPointsApi {

    /**
     * 获取所有用户积分数据
     *
     * @return 所有用户积分列表
     */
    @Operation(summary = "获取所有用户积分", description = "获取所有用户的积分数据及详细历史积分")
    @GetMapping("/all")
    ApiResponse<List<PointsVo>> getAllUserPoints();

    /**
     * 搜索单个用户积分数据
     *
     * @param userId 用户ID
     * @return 用户积分信息
     */
    @Operation(summary = "搜索用户积分", description = "根据用户ID搜索单个用户积分数据及详细历史积分")
    @GetMapping("/user/{userId}")
    ApiResponse<PointsVo> getUserPointsById(@Parameter(description = "用户ID") @PathVariable Long userId);

    /**
     * 获取用户积分历史
     *
     * @param userId 用户ID
     * @return 用户积分历史列表
     */
    @Operation(summary = "获取用户积分历史", description = "获取指定用户的详细积分历史记录")
    @GetMapping("/history/{userId}")
    ApiResponse<List<PointsHistoryVo>> getUserPointsHistory(@Parameter(description = "用户ID") @PathVariable Long userId);

    /**
     * 录入用户积分
     *
     * @param pointsAddVo 积分录入信息
     * @return 操作结果
     */
    @Operation(summary = "录入用户积分", description = "管理员录入用户当日获得的积分")
    @PostMapping("/add")
    ApiResponse<PointsVo> addUserPoints(@Valid @RequestBody PointsAddVo pointsAddVo);

    /**
     * 抵扣用户积分
     *
     * @param pointsDeductVo 积分抵扣信息
     * @return 操作结果
     */
    @Operation(summary = "抵扣用户积分", description = "管理员抵扣用户有效积分（用于门票抵扣等）")
    @PostMapping("/deduct")
    ApiResponse<PointsVo> deductUserPoints(@Valid @RequestBody PointsDeductVo pointsDeductVo);

}