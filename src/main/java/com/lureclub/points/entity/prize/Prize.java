package com.lureclub.points.entity.prize;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 奖品实体类
 *
 * @author system
 * @date 2025-06-19
 */
@Entity
@Table(name = "prizes")
public class Prize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 奖品名称
     */
    @Column(nullable = false, length = 200)
    @NotBlank(message = "奖品名称不能为空")
    private String name;

    /**
     * 奖品描述
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "奖品描述不能为空")
    private String description;

    /**
     * 奖品图片URL
     */
    @Column(name = "image_url", length = 500)
    private String imageUrl;

    /**
     * 排序序号
     */
    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

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
    public Prize() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    public Prize(String name, String description) {
        this();
        this.name = name;
        this.description = description;
    }

    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
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