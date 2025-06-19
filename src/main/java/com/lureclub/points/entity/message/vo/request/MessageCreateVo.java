package com.lureclub.points.entity.message.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 留言创建请求VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "留言创建请求参数")
public class MessageCreateVo {

    @Schema(description = "留言内容", example = "今天钓鱼收获不错！")
    @NotBlank(message = "留言内容不能为空")
    @Size(max = 1000, message = "留言内容不能超过1000个字符")
    private String content;

    // 构造函数
    public MessageCreateVo() {}

    public MessageCreateVo(String content) {
        this.content = content;
    }

    // Getter和Setter方法
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}