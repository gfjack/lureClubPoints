package com.lureclub.points.enums;

/**
 * 用户角色枚举
 *
 * @author system
 * @date 2025-06-19
 */
public enum UserRole {

    /**
     * 普通用户
     */
    USER("普通用户"),

    /**
     * 管理员
     */
    ADMIN("管理员");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}