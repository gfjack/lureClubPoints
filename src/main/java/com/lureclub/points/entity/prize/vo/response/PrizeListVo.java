package com.lureclub.points.entity.prize.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 奖品列表响应VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "奖品列表信息")
public class PrizeListVo {

    @Schema(description = "奖品ID")
    private Long id;

    @Schema(description = "奖品名称")
    private String name;

    @Schema(description = "奖品描述摘要（前100个字符）")
    private String summary;

    @Schema(description = "奖品图片URL")
    private String imageUrl;

    @Schema(description = "排序序号")
    private Integer sortOrder;

    // 构造函数
    public PrizeListVo() {}

    public PrizeListVo(Long id, String name, String description, String imageUrl, Integer sortOrder) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
        // 生成摘要，最多100个字符
        if (description != null) {
            this.summary = description.length() > 100 ? description.substring(0, 100) + "..." : description;
        }
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

}