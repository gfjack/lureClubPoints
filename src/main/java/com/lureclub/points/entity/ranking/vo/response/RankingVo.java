package com.lureclub.points.entity.ranking.vo.response;

import com.lureclub.points.enums.RankingType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

/**
 * 排行榜响应VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "排行榜信息")
public class RankingVo {

    @Schema(description = "排行榜类型")
    private RankingType rankingType;

    @Schema(description = "排行榜类型描述")
    private String rankingTypeDesc;

    @Schema(description = "统计日期")
    private LocalDate statisticsDate;

    @Schema(description = "排行榜列表")
    private List<RankingItemVo> rankingList;

    // 构造函数
    public RankingVo() {}

    public RankingVo(RankingType rankingType, LocalDate statisticsDate, List<RankingItemVo> rankingList) {
        this.rankingType = rankingType;
        this.rankingTypeDesc = rankingType != null ? rankingType.getDescription() : null;
        this.statisticsDate = statisticsDate;
        this.rankingList = rankingList;
    }

    // Getter和Setter方法
    public RankingType getRankingType() {
        return rankingType;
    }

    public void setRankingType(RankingType rankingType) {
        this.rankingType = rankingType;
        this.rankingTypeDesc = rankingType != null ? rankingType.getDescription() : null;
    }

    public String getRankingTypeDesc() {
        return rankingTypeDesc;
    }

    public void setRankingTypeDesc(String rankingTypeDesc) {
        this.rankingTypeDesc = rankingTypeDesc;
    }

    public LocalDate getStatisticsDate() {
        return statisticsDate;
    }

    public void setStatisticsDate(LocalDate statisticsDate) {
        this.statisticsDate = statisticsDate;
    }

    public List<RankingItemVo> getRankingList() {
        return rankingList;
    }

    public void setRankingList(List<RankingItemVo> rankingList) {
        this.rankingList = rankingList;
    }

}