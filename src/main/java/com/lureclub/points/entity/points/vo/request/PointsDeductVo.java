package com.lureclub.points.entity.points.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * 积分抵扣请求VO（管理员操作）
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "积分抵扣请求参数")
public class PointsDeductVo {

    @Schema(description = "用户ID", example = "1")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @Schema(description = "抵扣积分数量", example = "8")
    @NotNull(message = "积分数量不能为空")
    @Positive(message = "积分数量必须大于0")
    private Integer points;

    @Schema(description = "备注说明", example = "门票抵扣")
    private String remark;

    // 构造函数
    public PointsDeductVo() {}

    public PointsDeductVo(Long userId, Integer points, String remark) {
        this.userId = userId;
        this.points = points;
        this.remark = remark;
    }

    // Getter和Setter方法
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}