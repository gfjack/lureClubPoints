package com.lureclub.points.entity.admin.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 管理员创建请求VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "管理员创建请求参数")
public class AdminCreateVo {

    @Schema(description = "管理员用户名", example = "admin")
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度必须在3-20个字符之间")
    private String username;

    @Schema(description = "密码", example = "admin123")
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20个字符之间")
    private String password;

    @Schema(description = "真实姓名", example = "张三")
    private String realName;

    // 构造函数
    public AdminCreateVo() {}

    public AdminCreateVo(String username, String password, String realName) {
        this.username = username;
        this.password = password;
        this.realName = realName;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

}