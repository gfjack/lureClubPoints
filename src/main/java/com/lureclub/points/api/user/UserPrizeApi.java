package com.lureclub.points.api.user;

import com.lureclub.points.entity.prize.vo.response.PrizeVo;
import com.lureclub.points.entity.prize.vo.response.PrizeListVo;
import com.lureclub.points.entity.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户奖品API接口
 *
 * @author system
 * @date 2025-06-19
 */
@Tag(name = "用户奖品接口", description = "用户查看奖品相关接口")
@RequestMapping("/api/user/prize")
public interface UserPrizeApi {

    /**
     * 获取所有奖品信息
     *
     * @return 奖品列表
     */
    @Operation(summary = "获取所有奖品", description = "获取所有奖品信息，包含图片和描述")
    @GetMapping("")
    ApiResponse<List<PrizeListVo>> getAllPrizes();

    /**
     * 获取奖品详情
     *
     * @param id 奖品ID
     * @return 奖品详情
     */
    @Operation(summary = "获取奖品详情", description = "根据奖品ID获取奖品的详细信息")
    @GetMapping("/{id}")
    ApiResponse<PrizeVo> getPrizeDetail(@Parameter(description = "奖品ID") @PathVariable Long id);

}