package com.lureclub.points.entity.message.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 留言回复响应VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "留言回复信息")
public class MessageReplyVo {

    @Schema(description = "回复ID")
    private Long id;

    @Schema(description = "留言ID")
    private Long messageId;

    @Schema(description = "回复内容")
    private String content;

    @Schema(description = "是否公开可见")
    private Boolean isVisible;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // 构造函数
    public MessageReplyVo() {}

    public MessageReplyVo(Long id, Long messageId, String content, Boolean isVisible, LocalDateTime createTime) {
        this.id = id;
        this.messageId = messageId;
        this.content = content;
        this.isVisible = isVisible;
        this.createTime = createTime;
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

}