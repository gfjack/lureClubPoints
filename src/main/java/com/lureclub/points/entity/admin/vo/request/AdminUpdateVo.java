package com.lureclub.points.entity.admin.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

/**
 * 管理员更新请求VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "管理员更新请求参数")
public class AdminUpdateVo {

    @Schema(description = "真实姓名", example = "张三")
    @Size(max = 50, message = "真实姓名不能超过50个字符")
    private String realName;

    @Schema(description = "是否启用", example = "true")
    private Boolean isEnabled;

    @Schema(description = "新密码", example = "newpassword123")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20个字符之间")
    private String password;

    // 构造函数
    public AdminUpdateVo() {}

    public AdminUpdateVo(String realName, Boolean isEnabled, String password) {
        this.realName = realName;
        this.isEnabled = isEnabled;
        this.password = password;
    }

    // Getter和Setter方法
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}