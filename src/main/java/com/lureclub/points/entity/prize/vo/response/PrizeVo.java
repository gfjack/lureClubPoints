package com.lureclub.points.entity.prize.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 奖品详情响应VO（含图片URL）
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "奖品详情信息")
public class PrizeVo {

    @Schema(description = "奖品ID")
    private Long id;

    @Schema(description = "奖品名称")
    private String name;

    @Schema(description = "奖品描述")
    private String description;

    @Schema(description = "奖品图片URL")
    private String imageUrl;

    @Schema(description = "排序序号")
    private Integer sortOrder;

    @Schema(description = "是否启用")
    private Boolean isEnabled;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // 构造函数
    public PrizeVo() {}

    public PrizeVo(Long id, String name, String description, String imageUrl,
                   Integer sortOrder, Boolean isEnabled, LocalDateTime createTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
        this.isEnabled = isEnabled;
        this.createTime = createTime;
    }

    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

}