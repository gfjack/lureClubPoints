package com.lureclub.points.entity.prize.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * 奖品创建请求VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "奖品创建请求参数")
public class PrizeCreateVo {

    @Schema(description = "奖品名称", example = "钓鱼竿套装")
    @NotBlank(message = "奖品名称不能为空")
    private String name;

    @Schema(description = "奖品描述", example = "高品质碳素钓鱼竿，适合路亚钓法")
    @NotBlank(message = "奖品描述不能为空")
    private String description;

    @Schema(description = "排序序号", example = "1")
    private Integer sortOrder = 0;

    // 构造函数
    public PrizeCreateVo() {}

    public PrizeCreateVo(String name, String description, Integer sortOrder) {
        this.name = name;
        this.description = description;
        this.sortOrder = sortOrder;
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

}