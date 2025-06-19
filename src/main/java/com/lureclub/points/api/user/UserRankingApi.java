package com.lureclub.points.api.user;

import com.lureclub.points.entity.ranking.vo.response.RankingVo;
import com.lureclub.points.entity.ranking.vo.response.RankingItemVo;
import com.lureclub.points.entity.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户排行榜API接口
 *
 * @author system
 * @date 2025-06-19
 */
@Tag(name = "用户排行榜接口", description = "用户查看排行榜相关接口")
@RequestMapping("/api/user/ranking")
public interface UserRankingApi {

    /**
     * 获取当日排行榜
     *
     * @return 当日排行榜
     */
    @Operation(summary = "获取当日排行榜", description = "根据当日积分获取排行榜")
    @GetMapping("/daily")
    ApiResponse<List<RankingItemVo>> getDailyRanking();

    /**
     * 获取本周排行榜
     *
     * @return 本周排行榜
     */
    @Operation(summary = "获取本周排行榜", description = "根据本周积分获取排行榜")
    @GetMapping("/weekly")
    ApiResponse<List<RankingItemVo>> getWeeklyRanking();

    /**
     * 获取总排行榜
     *
     * @return 总排行榜
     */
    @Operation(summary = "获取总排行榜", description = "根据总积分获取排行榜")
    @GetMapping("/total")
    ApiResponse<List<RankingItemVo>> getTotalRanking();

}