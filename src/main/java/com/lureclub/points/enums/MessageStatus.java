package com.lureclub.points.enums;

/**
 * 留言状态枚举
 *
 * @author system
 * @date 2025-06-19
 */
public enum MessageStatus {

    /**
     * 待审核
     */
    PENDING("待审核"),

    /**
     * 已公开
     */
    PUBLISHED("已公开"),

    /**
     * 已回复
     */
    REPLIED("已回复"),

    /**
     * 已隐藏
     */
    HIDDEN("已隐藏");

    private final String description;

    MessageStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}