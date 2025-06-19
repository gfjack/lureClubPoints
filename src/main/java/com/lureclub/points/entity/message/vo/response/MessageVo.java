package com.lureclub.points.entity.message.vo.response;

import com.lureclub.points.enums.MessageStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 留言信息响应VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "留言信息")
public class MessageVo {

    @Schema(description = "留言ID")
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "留言内容")
    private String content;

    @Schema(description = "留言状态")
    private MessageStatus status;

    @Schema(description = "留言状态描述")
    private String statusDesc;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "回复列表")
    private List<MessageReplyVo> replies;

    // 构造函数
    public MessageVo() {}

    public MessageVo(Long id, Long userId, String username, String content,
                     MessageStatus status, LocalDateTime createTime, List<MessageReplyVo> replies) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.status = status;
        this.statusDesc = status != null ? status.getDescription() : null;
        this.createTime = createTime;
        this.replies = replies;
    }

    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
        this.statusDesc = status != null ? status.getDescription() : null;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public List<MessageReplyVo> getReplies() {
        return replies;
    }

    public void setReplies(List<MessageReplyVo> replies) {
        this.replies = replies;
    }

}