package com.lureclub.points.entity.user.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * 用户登录请求VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "用户登录请求参数")
public class UserLoginVo {

    /**
     * 用户名或手机号
     */
    @Schema(description = "用户名或手机号", example = "testuser")
    @NotBlank(message = "用户名或手机号不能为空")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码", example = "123456")
    @NotBlank(message = "密码不能为空")
    private String password;

    // 构造函数
    public UserLoginVo() {}

    public UserLoginVo(String username, String password) {
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