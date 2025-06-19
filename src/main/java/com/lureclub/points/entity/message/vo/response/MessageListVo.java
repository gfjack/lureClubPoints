package com.lureclub.points.entity.message.vo.response;

import com.lureclub.points.enums.MessageStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 留言列表响应VO（用于列表显示，内容较简化）
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "留言列表信息")
public class MessageListVo {

    @Schema(description = "留言ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "留言内容摘要（前50个字符）")
    private String summary;

    @Schema(description = "留言状态")
    private MessageStatus status;

    @Schema(description = "留言状态描述")
    private String statusDesc;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "回复数量")
    private Integer replyCount;

    // 构造函数
    public MessageListVo() {}

    public MessageListVo(Long id, String username, String content, MessageStatus status,
                         LocalDateTime createTime, Integer replyCount) {
        this.id = id;
        this.username = username;
        this.status = status;
        this.statusDesc = status != null ? status.getDescription() : null;
        this.createTime = createTime;
        this.replyCount = replyCount != null ? replyCount : 0;

        // 生成摘要，最多50个字符
        if (content != null) {
            this.summary = content.length() > 50 ? content.substring(0, 50) + "..." : content;
        }
    }

    // Getter和Setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public MessageStatus getStatus() { return status; }
    public void setStatus(MessageStatus status) {
        this.status = status;
        this.statusDesc = status != null ? status.getDescription() : null;
    }

    public String getStatusDesc() { return statusDesc; }
    public void setStatusDesc(String statusDesc) { this.statusDesc = statusDesc; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public Integer getReplyCount() { return replyCount; }
    public void setReplyCount(Integer replyCount) { this.replyCount = replyCount; }
}