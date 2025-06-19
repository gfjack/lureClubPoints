package com.lureclub.points.enums;

/**
 * 排行榜类型枚举
 *
 * @author system
 * @date 2025-06-19
 */
public enum RankingType {

    /**
     * 当日排行榜
     */
    DAILY("当日排行榜"),

    /**
     * 本周排行榜
     */
    WEEKLY("本周排行榜"),

    /**
     * 总排行榜
     */
    TOTAL("总排行榜");

    private final String description;

    RankingType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}