package com.lureclub.points.controller.admin;

import com.lureclub.points.api.admin.AdminRankingApi;
import com.lureclub.points.entity.ranking.vo.response.RankingItemVo;
import com.lureclub.points.entity.common.ApiResponse;
import com.lureclub.points.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 管理员排行榜控制器实现
 *
 * @author system
 * @date 2025-06-19
 */
@RestController
@RequestMapping("/api/admin/ranking")
public class AdminRankingController implements AdminRankingApi {

    @Autowired
    private RankingService rankingService;

    /**
     * 获取当日排行榜实现
     */
    @Override
    @GetMapping("/daily")
    public ApiResponse<List<RankingItemVo>> getDailyRanking() {
        try {
            List<RankingItemVo> result = rankingService.getDailyRanking();
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("获取当日排行榜失败: " + e.getMessage());
        }
    }

    /**
     * 获取本周排行榜实现
     */
    @Override
    @GetMapping("/weekly")
    public ApiResponse<List<RankingItemVo>> getWeeklyRanking() {
        try {
            List<RankingItemVo> result = rankingService.getWeeklyRanking();
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("获取本周排行榜失败: " + e.getMessage());
        }
    }

    /**
     * 获取总排行榜实现
     */
    @Override
    @GetMapping("/total")
    public ApiResponse<List<RankingItemVo>> getTotalRanking() {
        try {
            List<RankingItemVo> result = rankingService.getTotalRanking();
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("获取总排行榜失败: " + e.getMessage());
        }
    }

}