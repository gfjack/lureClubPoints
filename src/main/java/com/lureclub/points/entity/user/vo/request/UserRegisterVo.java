package com.lureclub.points.entity.user.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 用户注册请求VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "用户注册请求参数")
public class UserRegisterVo {

    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "testuser")
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度必须在3-20个字符之间")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码", example = "123456")
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20个字符之间")
    private String password;

    /**
     * 确认密码
     */
    @Schema(description = "确认密码", example = "123456")
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "13800138000")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    // 构造函数
    public UserRegisterVo() {}

    public UserRegisterVo(String username, String password, String confirmPassword, String phone) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}