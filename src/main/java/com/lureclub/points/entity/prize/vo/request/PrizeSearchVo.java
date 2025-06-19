package com.lureclub.points.entity.prize.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 奖品搜索请求VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "奖品搜索请求参数")
public class PrizeSearchVo {

    @Schema(description = "奖品名称关键词", example = "钓鱼竿")
    private String name;

    @Schema(description = "奖品描述关键词", example = "路亚")
    private String description;

    @Schema(description = "是否启用", example = "true")
    private Boolean isEnabled;

    @Schema(description = "最小排序序号", example = "1")
    private Integer minSortOrder;

    @Schema(description = "最大排序序号", example = "100")
    private Integer maxSortOrder;

    @Schema(description = "创建开始日期", example = "2025-06-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "创建结束日期", example = "2025-06-30")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Schema(description = "是否有图片")
    private Boolean hasImage;

    // 构造函数
    public PrizeSearchVo() {}

    public PrizeSearchVo(String name, String description, Boolean isEnabled,
                         Integer minSortOrder, Integer maxSortOrder,
                         LocalDate startDate, LocalDate endDate, Boolean hasImage) {
        this.name = name;
        this.description = description;
        this.isEnabled = isEnabled;
        this.minSortOrder = minSortOrder;
        this.maxSortOrder = maxSortOrder;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hasImage = hasImage;
    }

    // Getter和Setter方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Integer getMinSortOrder() {
        return minSortOrder;
    }

    public void setMinSortOrder(Integer minSortOrder) {
        this.minSortOrder = minSortOrder;
    }

    public Integer getMaxSortOrder() {
        return maxSortOrder;
    }

    public void setMaxSortOrder(Integer maxSortOrder) {
        this.maxSortOrder = maxSortOrder;
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

    public Boolean getHasImage() {
        return hasImage;
    }

    public void setHasImage(Boolean hasImage) {
        this.hasImage = hasImage;
    }

}