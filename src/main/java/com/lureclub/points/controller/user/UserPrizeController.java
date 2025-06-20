package com.lureclub.points.controller.user;

import com.lureclub.points.api.user.UserPrizeApi;
import com.lureclub.points.entity.prize.vo.response.PrizeVo;
import com.lureclub.points.entity.prize.vo.response.PrizeListVo;
import com.lureclub.points.entity.common.ApiResponse;
import com.lureclub.points.service.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户奖品控制器实现
 *
 * @author system
 * @date 2025-06-19
 */
@RestController
@RequestMapping("/api/user/prize")
public class UserPrizeController implements UserPrizeApi {

    @Autowired
    private PrizeService prizeService;

    /**
     * 获取所有奖品信息实现
     */
    @Override
    @GetMapping("")
    public ApiResponse<List<PrizeListVo>> getAllPrizes() {
        try {
            List<PrizeListVo> result = prizeService.getAllPrizes();
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("获取奖品列表失败: " + e.getMessage());
        }
    }

    /**
     * 获取奖品详情实现
     */
    @Override
    @GetMapping("/{id}")
    public ApiResponse<PrizeVo> getPrizeDetail(Long id) {
        try {
            PrizeVo result = prizeService.getPrizeDetail(id);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("获取奖品详情失败: " + e.getMessage());
        }
    }

}