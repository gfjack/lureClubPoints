package com.lureclub.points.entity.user.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 登录响应VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "登录响应信息")
public class LoginVo {

    @Schema(description = "访问令牌")
    private String token;

    @Schema(description = "令牌类型", example = "Bearer")
    private String tokenType = "Bearer";

    @Schema(description = "用户信息")
    private UserVo userInfo;

    // 构造函数
    public LoginVo() {}

    public LoginVo(String token, UserVo userInfo) {
        this.token = token;
        this.userInfo = userInfo;
    }

    public LoginVo(String token, String tokenType, UserVo userInfo) {
        this.token = token;
        this.tokenType = tokenType;
        this.userInfo = userInfo;
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

    public UserVo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserVo userInfo) {
        this.userInfo = userInfo;
    }

}