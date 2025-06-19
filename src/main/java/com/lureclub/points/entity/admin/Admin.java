package com.lureclub.points.entity.admin;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 管理员实体类
 *
 * @author system
 * @date 2025-06-19
 */
@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 管理员用户名
     */
    @Column(unique = true, nullable = false, length = 50)
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @Column(nullable = false, length = 255)
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 真实姓名
     */
    @Column(name = "real_name", length = 50)
    private String realName;

    /**
     * 是否启用
     */
    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    /**
     * 是否删除 (0:未删除 1:已删除)
     */
    @Column(name = "is_deleted", nullable = false)
    private Integer isDeleted = 0;

    // 构造函数
    public Admin() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    public Admin(String username, String password) {
        this();
        this.username = username;
        this.password = password;
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

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @PreUpdate
    public void preUpdate() {
        this.updateTime = LocalDateTime.now();
    }

}