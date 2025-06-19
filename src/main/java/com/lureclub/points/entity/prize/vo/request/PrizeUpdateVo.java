package com.lureclub.points.entity.prize.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 奖品更新请求VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "奖品更新请求参数")
public class PrizeUpdateVo {

    @Schema(description = "奖品名称", example = "钓鱼竿套装")
    private String name;

    @Schema(description = "奖品描述", example = "高品质碳素钓鱼竿，适合路亚钓法")
    private String description;

    @Schema(description = "排序序号", example = "1")
    private Integer sortOrder;

    @Schema(description = "是否启用", example = "true")
    private Boolean isEnabled;

    // 构造函数
    public PrizeUpdateVo() {}

    public PrizeUpdateVo(String name, String description, Integer sortOrder, Boolean isEnabled) {
        this.name = name;
        this.description = description;
        this.sortOrder = sortOrder;
        this.isEnabled = isEnabled;
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

}