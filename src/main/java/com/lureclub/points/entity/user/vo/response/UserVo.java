package com.lureclub.points.entity.user.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 用户信息响应VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "用户信息")
public class UserVo {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // 构造函数
    public UserVo() {}

    public UserVo(Long id, String username, String phone, LocalDateTime createTime) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.createTime = createTime;
    }

    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

}