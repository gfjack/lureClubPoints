package com.lureclub.points.entity.announcement.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

/**
 * 公告列表响应VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "公告列表信息")
public class AnnouncementListVo {

    @Schema(description = "公告ID")
    private Long id;

    @Schema(description = "公告标题")
    private String title;

    @Schema(description = "公告内容摘要（前100个字符）")
    private String summary;

    @Schema(description = "发布日期")
    private LocalDate publishDate;

    @Schema(description = "是否置顶")
    private Boolean isTop;

    // 构造函数
    public AnnouncementListVo() {}

    public AnnouncementListVo(Long id, String title, String content, LocalDate publishDate, Boolean isTop) {
        this.id = id;
        this.title = title;
        this.publishDate = publishDate;
        this.isTop = isTop;
        // 生成摘要，最多100个字符
        if (content != null) {
            this.summary = content.length() > 100 ? content.substring(0, 100) + "..." : content;
        }
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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