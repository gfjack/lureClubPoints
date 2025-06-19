package com.lureclub.points.entity.message;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 留言回复实体类
 *
 * @author system
 * @date 2025-06-19
 */
@Entity
@Table(name = "message_replies")
public class MessageReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 留言ID
     */
    @Column(name = "message_id", nullable = false)
    private Long messageId;

    /**
     * 回复内容
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "回复内容不能为空")
    private String content;

    /**
     * 是否公开可见
     */
    @Column(name = "is_visible", nullable = false)
    private Boolean isVisible = true;

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
    public MessageReply() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    public MessageReply(Long messageId, String content, Boolean isVisible) {
        this();
        this.messageId = messageId;
        this.content = content;
        this.isVisible = isVisible;
    }

    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
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