package com.lureclub.points.entity.announcement.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 公告搜索请求VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "公告搜索请求参数")
public class AnnouncementSearchVo {

    @Schema(description = "开始日期", example = "2025-06-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "结束日期", example = "2025-06-30")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Schema(description = "关键词搜索（标题或内容）", example = "钓鱼")
    private String keyword;

    @Schema(description = "是否只显示置顶公告")
    private Boolean onlyTop;

    // 构造函数
    public AnnouncementSearchVo() {}

    public AnnouncementSearchVo(LocalDate startDate, LocalDate endDate, String keyword, Boolean onlyTop) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.keyword = keyword;
        this.onlyTop = onlyTop;
    }

    // Getter和Setter方法
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

    public Boolean getOnlyTop() {
        return onlyTop;
    }

    public void setOnlyTop(Boolean onlyTop) {
        this.onlyTop = onlyTop;
    }

}