package com.lureclub.points.api.admin;

import com.lureclub.points.entity.prize.vo.request.PrizeCreateVo;
import com.lureclub.points.entity.prize.vo.request.PrizeUpdateVo;
import com.lureclub.points.entity.prize.vo.response.PrizeVo;
import com.lureclub.points.entity.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 管理员奖品管理API接口
 *
 * @author system
 * @date 2025-06-19
 */
@Tag(name = "管理员奖品管理接口", description = "管理员奖品管理相关接口")
@RequestMapping("/api/admin/prize")
public interface AdminPrizeApi {

    /**
     * 获取所有奖品（管理员）
     *
     * @return 奖品列表
     */
    @Operation(summary = "获取所有奖品", description = "管理员获取所有奖品列表")
    @GetMapping("")
    ApiResponse<List<PrizeVo>> getAllPrizes();

    /**
     * 创建奖品（含图片上传）
     *
     * @param createVo 奖品信息
     * @param imageFile 奖品图片
     * @return 创建结果
     */
    @Operation(summary = "创建奖品", description = "管理员创建新奖品，可上传图片")
    @PostMapping("")
    ApiResponse<PrizeVo> createPrize(@Valid @ModelAttribute PrizeCreateVo createVo,
                                     @Parameter(description = "奖品图片") @RequestParam(required = false) MultipartFile imageFile);

    /**
     * 更新奖品（含图片上传）
     *
     * @param id 奖品ID
     * @param updateVo 更新信息
     * @param imageFile 奖品图片（可选）
     * @return 更新结果
     */
    @Operation(summary = "更新奖品", description = "管理员更新奖品信息，可更新图片")
    @PutMapping("/{id}")
    ApiResponse<PrizeVo> updatePrize(@Parameter(description = "奖品ID") @PathVariable Long id,
                                     @Valid @ModelAttribute PrizeUpdateVo updateVo,
                                     @Parameter(description = "奖品图片") @RequestParam(required = false) MultipartFile imageFile);

    /**
     * 删除奖品
     *
     * @param id 奖品ID
     * @return 删除结果
     */
    @Operation(summary = "删除奖品", description = "管理员删除奖品")
    @DeleteMapping("/{id}")
    ApiResponse<String> deletePrize(@Parameter(description = "奖品ID") @PathVariable Long id);

}