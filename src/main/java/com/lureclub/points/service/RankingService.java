package com.lureclub.points.service;

import com.lureclub.points.entity.ranking.vo.response.RankingItemVo;

import java.util.List;

/**
 * 排行榜服务接口
 *
 * @author system
 * @date 2025-06-19
 */
public interface RankingService {

    /**
     * 获取当日排行榜
     *
     * @return 当日排行榜列表
     */
    List<RankingItemVo> getDailyRanking();

    /**
     * 获取本周排行榜
     *
     * @return 本周排行榜列表
     */
    List<RankingItemVo> getWeeklyRanking();

    /**
     * 获取总排行榜
     *
     * @return 总排行榜列表
     */
    List<RankingItemVo> getTotalRanking();

}