package com.lureclub.points.entity.message.vo.request;

import com.lureclub.points.enums.MessageStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

/**
 * 留言更新请求VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "留言更新请求参数")
public class MessageUpdateVo {

    @Schema(description = "留言内容", example = "今天钓鱼收获不错！")
    @Size(max = 1000, message = "留言内容不能超过1000个字符")
    private String content;

    @Schema(description = "留言状态", example = "PUBLISHED")
    private MessageStatus status;

    @Schema(description = "管理员备注", example = "用户留言已审核通过")
    @Size(max = 500, message = "备注不能超过500个字符")
    private String adminRemark;

    // 构造函数
    public MessageUpdateVo() {}

    public MessageUpdateVo(String content, MessageStatus status, String adminRemark) {
        this.content = content;
        this.status = status;
        this.adminRemark = adminRemark;
    }

    // Getter和Setter方法
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
    }

    public String getAdminRemark() {
        return adminRemark;
    }

    public void setAdminRemark(String adminRemark) {
        this.adminRemark = adminRemark;
    }

}