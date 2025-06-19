package com.lureclub.points.entity.announcement.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 公告详情响应VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "公告详情信息")
public class AnnouncementVo {

    @Schema(description = "公告ID")
    private Long id;

    @Schema(description = "公告标题")
    private String title;

    @Schema(description = "公告内容")
    private String content;

    @Schema(description = "发布日期")
    private LocalDate publishDate;

    @Schema(description = "是否置顶")
    private Boolean isTop;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // 构造函数
    public AnnouncementVo() {}

    public AnnouncementVo(Long id, String title, String content, LocalDate publishDate,
                          Boolean isTop, LocalDateTime createTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
        this.isTop = isTop;
        this.createTime = createTime;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

}