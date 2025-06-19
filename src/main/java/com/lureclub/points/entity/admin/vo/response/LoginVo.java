package com.lureclub.points.entity.admin.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 管理员登录响应VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "管理员登录响应信息")
public class LoginVo {

    @Schema(description = "访问令牌")
    private String token;

    @Schema(description = "令牌类型", example = "Bearer")
    private String tokenType = "Bearer";

    @Schema(description = "管理员信息")
    private AdminVo adminInfo;

    // 构造函数
    public LoginVo() {}

    public LoginVo(String token, AdminVo adminInfo) {
        this.token = token;
        this.adminInfo = adminInfo;
    }

    public LoginVo(String token, String tokenType, AdminVo adminInfo) {
        this.token = token;
        this.tokenType = tokenType;
        this.adminInfo = adminInfo;
    }

    // Getter和Setter方法
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public AdminVo getAdminInfo() {
        return adminInfo;
    }

    public void setAdminInfo(AdminVo adminInfo) {
        this.adminInfo = adminInfo;
    }

}