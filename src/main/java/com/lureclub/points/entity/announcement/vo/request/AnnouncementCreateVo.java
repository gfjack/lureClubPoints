package com.lureclub.points.entity.announcement.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 公告创建请求VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "公告创建请求参数")
public class AnnouncementCreateVo {

    @Schema(description = "公告标题", example = "钓场开放通知")
    @NotBlank(message = "公告标题不能为空")
    private String title;

    @Schema(description = "公告内容", example = "本钓场将于明日正式开放...")
    @NotBlank(message = "公告内容不能为空")
    private String content;

    @Schema(description = "发布日期", example = "2025-06-20")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishDate;

    @Schema(description = "是否置顶", example = "false")
    private Boolean isTop = false;

    // 构造函数
    public AnnouncementCreateVo() {}

    public AnnouncementCreateVo(String title, String content, LocalDate publishDate, Boolean isTop) {
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
        this.isTop = isTop;
    }

    // Getter和Setter方法
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

}