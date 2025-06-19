package com.lureclub.points.entity.ranking.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 排行榜项目响应VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "排行榜项目信息")
public class RankingItemVo {

    @Schema(description = "排名")
    private Integer rank;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "积分数量")
    private Integer points;

    // 构造函数
    public RankingItemVo() {}

    public RankingItemVo(Integer rank, Long userId, String username, Integer points) {
        this.rank = rank;
        this.userId = userId;
        this.username = username;
        this.points = points;
    }

    // Getter和Setter方法
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

}