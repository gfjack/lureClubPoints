package com.lureclub.points.entity.admin.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 管理员信息响应VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "管理员信息")
public class AdminVo {

    @Schema(description = "管理员ID")
    private Long id;

    @Schema(description = "管理员用户名")
    private String username;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "是否启用")
    private Boolean isEnabled;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // 构造函数
    public AdminVo() {}

    public AdminVo(Long id, String username, String realName, Boolean isEnabled, LocalDateTime createTime) {
        this.id = id;
        this.username = username;
        this.realName = realName;
        this.isEnabled = isEnabled;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

}