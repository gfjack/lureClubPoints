package com.lureclub.points.entity.points.vo.response;

import com.lureclub.points.enums.PointsType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 积分历史响应VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "积分历史信息")
public class PointsHistoryVo {

    @Schema(description = "历史记录ID")
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "积分类型")
    private PointsType pointsType;

    @Schema(description = "积分类型描述")
    private String pointsTypeDesc;

    @Schema(description = "积分数量")
    private Integer points;

    @Schema(description = "操作日期")
    private LocalDate operationDate;

    @Schema(description = "备注说明")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // 构造函数
    public PointsHistoryVo() {}

    public PointsHistoryVo(Long id, Long userId, PointsType pointsType, Integer points,
                           LocalDate operationDate, String remark, LocalDateTime createTime) {
        this.id = id;
        this.userId = userId;
        this.pointsType = pointsType;
        this.pointsTypeDesc = pointsType != null ? pointsType.getDescription() : null;
        this.points = points;
        this.operationDate = operationDate;
        this.remark = remark;
        this.createTime = createTime;
    }

    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public PointsType getPointsType() {
        return pointsType;
    }

    public void setPointsType(PointsType pointsType) {
        this.pointsType = pointsType;
        this.pointsTypeDesc = pointsType != null ? pointsType.getDescription() : null;
    }

    public String getPointsTypeDesc() {
        return pointsTypeDesc;
    }

    public void setPointsTypeDesc(String pointsTypeDesc) {
        this.pointsTypeDesc = pointsTypeDesc;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

}