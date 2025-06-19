package com.lureclub.points.controller.admin;

import com.lureclub.points.api.admin.AdminPrizeApi;
import com.lureclub.points.entity.prize.vo.request.PrizeCreateVo;
import com.lureclub.points.entity.prize.vo.request.PrizeUpdateVo;
import com.lureclub.points.entity.prize.vo.response.PrizeVo;
import com.lureclub.points.entity.common.ApiResponse;
import com.lureclub.points.service.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 管理员奖品控制器实现
 *
 * @author system
 * @date 2025-06-19
 */
@RestController
public class AdminPrizeController implements AdminPrizeApi {

    @Autowired
    private PrizeService prizeService;

    /**
     * 获取所有奖品实现（管理员）
     */
    @Override
    public ApiResponse<List<PrizeVo>> getAllPrizes() {
        try {
            List<PrizeVo> result = prizeService.getAllPrizesForAdmin();
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("获取奖品列表失败: " + e.getMessage());
        }
    }

    /**
     * 创建奖品实现
     */
    @Override
    public ApiResponse<PrizeVo> createPrize(@Valid PrizeCreateVo createVo, MultipartFile imageFile) {
        try {
            PrizeVo result = prizeService.createPrize(createVo, imageFile);
            return ApiResponse.success(result, "奖品创建成功");
        } catch (Exception e) {
            return ApiResponse.error("创建奖品失败: " + e.getMessage());
        }
    }

    /**
     * 更新奖品实现
     */
    @Override
    public ApiResponse<PrizeVo> updatePrize(Long id, @Valid PrizeUpdateVo updateVo, MultipartFile imageFile) {
        try {
            PrizeVo result = prizeService.updatePrize(id, updateVo, imageFile);
            return ApiResponse.success(result, "奖品更新成功");
        } catch (Exception e) {
            return ApiResponse.error("更新奖品失败: " + e.getMessage());
        }
    }

    /**
     * 删除奖品实现
     */
    @Override
    public ApiResponse<String> deletePrize(Long id) {
        try {
            prizeService.deletePrize(id);
            return ApiResponse.success("奖品删除成功");
        } catch (Exception e) {
            return ApiResponse.error("删除奖品失败: " + e.getMessage());
        }
    }

}