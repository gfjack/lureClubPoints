package com.lureclub.points.entity.announcement;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 公告实体类
 *
 * @author system
 * @date 2025-06-19
 */
@Entity
@Table(name = "announcements")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 公告标题
     */
    @Column(nullable = false, length = 200)
    @NotBlank(message = "公告标题不能为空")
    private String title;

    /**
     * 公告内容
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "公告内容不能为空")
    private String content;

    /**
     * 发布日期
     */
    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    /**
     * 是否置顶
     */
    @Column(name = "is_top", nullable = false)
    private Boolean isTop = false;

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
    public Announcement() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.publishDate = LocalDate.now();
    }

    public Announcement(String title, String content) {
        this();
        this.title = title;
        this.content = content;
    }

    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public Boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
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