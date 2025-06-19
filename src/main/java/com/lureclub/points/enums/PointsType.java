package com.lureclub.points.enums;

/**
 * 积分类型枚举
 *
 * @author system
 * @date 2025-06-19
 */
public enum PointsType {

    /**
     * 类型1: 获得积分（管理员录入）
     */
    EARNED("获得积分"),

    /**
     * 类型2: 抵扣积分（管理员抵扣操作）
     */
    DEDUCTED("抵扣积分"),

    /**
     * 类型3: 管理员调整
     */
    ADMIN_ADJUSTMENT("管理员调整");

    private final String description;

    PointsType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}