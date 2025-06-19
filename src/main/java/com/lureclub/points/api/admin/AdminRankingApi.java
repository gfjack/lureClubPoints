package com.lureclub.points.api.admin;

import com.lureclub.points.entity.ranking.vo.response.RankingItemVo;
import com.lureclub.points.entity.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员排行榜API接口
 *
 * @author system
 * @date 2025-06-19
 */
@Tag(name = "管理员排行榜接口", description = "管理员查看排行榜相关接口")
@RequestMapping("/api/admin/ranking")
public interface AdminRankingApi {

    /**
     * 获取当日排行榜
     *
     * @return 当日排行榜
     */
    @Operation(summary = "获取当日排行榜", description = "管理员查看当日排行榜")
    @GetMapping("/daily")
    ApiResponse<List<RankingItemVo>> getDailyRanking();

    /**
     * 获取本周排行榜
     *
     * @return 本周排行榜
     */
    @Operation(summary = "获取本周排行榜", description = "管理员查看本周排行榜")
    @GetMapping("/weekly")
    ApiResponse<List<RankingItemVo>> getWeeklyRanking();

    /**
     * 获取总排行榜
     *
     * @return 总排行榜
     */
    @Operation(summary = "获取总排行榜", description = "管理员查看总排行榜")
    @GetMapping("/total")
    ApiResponse<List<RankingItemVo>> getTotalRanking();

}