package com.lureclub.points.controller.admin;

import com.lureclub.points.api.admin.AdminPointsApi;
import com.lureclub.points.entity.points.vo.request.PointsAddVo;
import com.lureclub.points.entity.points.vo.request.PointsDeductVo;
import com.lureclub.points.entity.points.vo.response.PointsVo;
import com.lureclub.points.entity.points.vo.response.PointsHistoryVo;
import com.lureclub.points.entity.common.ApiResponse;
import com.lureclub.points.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 管理员积分控制器实现
 *
 * @author system
 * @date 2025-06-19
 */
@RestController
@RequestMapping("/api/admin/points")
public class AdminPointsController implements AdminPointsApi {

    @Autowired
    private PointsService pointsService;

    /**
     * 获取所有用户积分数据实现
     */
    @Override
    @GetMapping("/all")
    public ApiResponse<List<PointsVo>> getAllUserPoints() {
        try {
            // 这里需要在PointsService中添加getAllUserPoints方法
            List<PointsVo> result = pointsService.getAllUserPoints();
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("获取用户积分失败: " + e.getMessage());
        }
    }

    /**
     * 搜索单个用户积分数据实现
     */
    @Override
    @GetMapping("/user/{userId}")
    public ApiResponse<PointsVo> getUserPointsById(Long userId) {
        try {
            PointsVo result = pointsService.getUserPoints(userId);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("获取用户积分失败: " + e.getMessage());
        }
    }

    /**
     * 获取用户积分历史实现
     */
    @Override
    @GetMapping("/history/{userId}")
    public ApiResponse<List<PointsHistoryVo>> getUserPointsHistory(Long userId) {
        try {
            List<PointsHistoryVo> result = pointsService.getUserPointsHistory(userId);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("获取积分历史失败: " + e.getMessage());
        }
    }

    /**
     * 录入用户积分实现
     */
    @Override
    @PostMapping("/add")
    public ApiResponse<PointsVo> addUserPoints(@Valid PointsAddVo pointsAddVo) {
        try {
            PointsVo result = pointsService.addUserPoints(pointsAddVo);
            return ApiResponse.success(result, "积分录入成功");
        } catch (Exception e) {
            return ApiResponse.error("积分录入失败: " + e.getMessage());
        }
    }

    /**
     * 抵扣用户积分实现
     */
    @Override
    @PostMapping("/deduct")
    public ApiResponse<PointsVo> deductUserPoints(@Valid PointsDeductVo pointsDeductVo) {
        try {
            PointsVo result = pointsService.deductUserPoints(pointsDeductVo);
            return ApiResponse.success(result, "积分抵扣成功");
        } catch (Exception e) {
            return ApiResponse.error("积分抵扣失败: " + e.getMessage());
        }
    }

}