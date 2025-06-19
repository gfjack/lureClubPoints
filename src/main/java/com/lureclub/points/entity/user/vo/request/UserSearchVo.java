package com.lureclub.points.entity.user.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 用户搜索请求VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "用户搜索请求参数")
public class UserSearchVo {

    @Schema(description = "用户名关键词", example = "test")
    private String username;

    @Schema(description = "手机号关键词", example = "138")
    private String phone;

    // 构造函数
    public UserSearchVo() {}

    public UserSearchVo(String username, String phone) {
        this.username = username;
        this.phone = phone;
    }

    // Getter和Setter方法
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}