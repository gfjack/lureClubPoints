package com.lureclub.points.entity.admin.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * 管理员登录请求VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "管理员登录请求参数")
public class AdminLoginVo {

    @Schema(description = "管理员用户名", example = "admin")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码", example = "admin123")
    @NotBlank(message = "密码不能为空")
    private String password;

    // 构造函数
    public AdminLoginVo() {}

    public AdminLoginVo(String username, String password) {
        this.username = username;
        this.password = password;
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

}