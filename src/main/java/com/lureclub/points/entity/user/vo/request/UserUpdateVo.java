package com.lureclub.points.entity.user.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 用户更新请求VO（管理员用）
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "用户更新请求参数")
public class UserUpdateVo {

    @Schema(description = "用户名", example = "testuser")
    @Size(min = 3, max = 20, message = "用户名长度必须在3-20个字符之间")
    private String username;

    @Schema(description = "密码", example = "123456")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20个字符之间")
    private String password;

    @Schema(description = "手机号", example = "13800138000")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    // 构造函数
    public UserUpdateVo() {}

    public UserUpdateVo(String username, String password, String phone) {
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

    // Getter和Setter方法
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}