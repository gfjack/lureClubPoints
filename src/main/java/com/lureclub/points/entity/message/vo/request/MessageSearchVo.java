package com.lureclub.points.entity.message.vo.request;

import com.lureclub.points.enums.MessageStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 留言搜索请求VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "留言搜索请求参数")
public class MessageSearchVo {

    @Schema(description = "用户ID", example = "1")
    private Long userId;

    @Schema(description = "用户名关键词", example = "张三")
    private String username;

    @Schema(description = "留言状态", example = "PUBLISHED")
    private MessageStatus status;

    @Schema(description = "开始日期", example = "2025-06-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "结束日期", example = "2025-06-30")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Schema(description = "留言内容关键词", example = "钓鱼")
    private String keyword;

    @Schema(description = "是否只显示有回复的留言")
    private Boolean hasReply;

    // 构造函数
    public MessageSearchVo() {}

    public MessageSearchVo(Long userId, String username, MessageStatus status,
                           LocalDate startDate, LocalDate endDate, String keyword, Boolean hasReply) {
        this.userId = userId;
        this.username = username;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.keyword = keyword;
        this.hasReply = hasReply;
    }

    // Getter和Setter方法
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

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Boolean getHasReply() {
        return hasReply;
    }

    public void setHasReply(Boolean hasReply) {
        this.hasReply = hasReply;
    }

}