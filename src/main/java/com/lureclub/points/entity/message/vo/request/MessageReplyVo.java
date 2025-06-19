package com.lureclub.points.entity.message.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * 留言回复请求VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "留言回复请求参数")
public class MessageReplyVo {

    @Schema(description = "回复内容", example = "感谢您的留言！")
    @NotBlank(message = "回复内容不能为空")
    private String content;

    @Schema(description = "是否公开可见", example = "true")
    private Boolean isVisible = true;

    // 构造函数
    public MessageReplyVo() {}

    public MessageReplyVo(String content, Boolean isVisible) {
        this.content = content;
        this.isVisible = isVisible;
    }

    // Getter和Setter方法
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

}