package com.lureclub.points.entity.points;

import com.lureclub.points.enums.PointsType;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 积分历史实体类（修复版：增加余额追踪字段）
 *
 * @author system
 * @date 2025-06-19
 */
@Entity
@Table(name = "points_history")
public class PointsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 积分类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "points_type", nullable = false)
    private PointsType pointsType;

    /**
     * 积分数量（正数为获得，负数为抵扣）
     */
    @Column(name = "points", nullable = false)
    private Integer points;

    /**
     * 操作日期
     */
    @Column(name = "operation_date", nullable = false)
    private LocalDate operationDate;

    /**
     * 修复：新增操作后有效积分余额字段
     */
    @Column(name = "effective_points_after", nullable = false)
    private Integer effectivePointsAfter = 0;

    /**
     * 修复：新增操作后总积分余额字段
     */
    @Column(name = "total_points_after", nullable = false)
    private Integer totalPointsAfter = 0;

    /**
     * 备注说明
     */
    @Column(name = "remark", length = 500)
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    // 构造函数
    public PointsHistory() {
        this.createTime = LocalDateTime.now();
        this.operationDate = LocalDate.now();
    }

    public PointsHistory(Long userId, PointsType pointsType, Integer points, String remark) {
        this();
        this.userId = userId;
        this.pointsType = pointsType;
        this.points = points;
        this.remark = remark;
    }

    /**
     * 修复：增加完整的构造函数，包含余额信息
     */
    public PointsHistory(Long userId, PointsType pointsType, Integer points,
                         Integer effectivePointsAfter, Integer totalPointsAfter, String remark) {
        this();
        this.userId = userId;
        this.pointsType = pointsType;
        this.points = points;
        this.effectivePointsAfter = effectivePointsAfter != null ? effectivePointsAfter : 0;
        this.totalPointsAfter = totalPointsAfter != null ? totalPointsAfter : 0;
        this.remark = remark;
    }

    /**
     * 修复：便捷方法创建获得积分记录
     */
    public static PointsHistory createEarnedRecord(Long userId, Integer points,
                                                   Integer effectivePointsAfter, Integer totalPointsAfter, String remark) {
        return new PointsHistory(userId, PointsType.EARNED, points, effectivePointsAfter, totalPointsAfter, remark);
    }

    /**
     * 修复：便捷方法创建抵扣积分记录
     */
    public static PointsHistory createDeductedRecord(Long userId, Integer points,
                                                     Integer effectivePointsAfter, Integer totalPointsAfter, String remark) {
        return new PointsHistory(userId, PointsType.DEDUCTED, -Math.abs(points), effectivePointsAfter, totalPointsAfter, remark);
    }

    /**
     * 修复：便捷方法创建管理员调整记录
     */
    public static PointsHistory createAdjustmentRecord(Long userId, Integer points,
                                                       Integer effectivePointsAfter, Integer totalPointsAfter, String remark) {
        return new PointsHistory(userId, PointsType.ADMIN_ADJUSTMENT, points, effectivePointsAfter, totalPointsAfter, remark);
    }

    /**
     * 检查是否是获得积分记录
     */
    public boolean isEarnedRecord() {
        return pointsType == PointsType.EARNED && points > 0;
    }

    /**
     * 检查是否是抵扣积分记录
     */
    public boolean isDeductedRecord() {
        return pointsType == PointsType.DEDUCTED && points < 0;
    }

    /**
     * 获取积分的绝对值
     */
    public Integer getAbsolutePoints() {
        return Math.abs(points);
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

    /**
     * 修复：新增getter/setter
     */
    public Integer getEffectivePointsAfter() {
        return effectivePointsAfter;
    }

    public void setEffectivePointsAfter(Integer effectivePointsAfter) {
        this.effectivePointsAfter = effectivePointsAfter != null ? effectivePointsAfter : 0;
    }

    public Integer getTotalPointsAfter() {
        return totalPointsAfter;
    }

    public void setTotalPointsAfter(Integer totalPointsAfter) {
        this.totalPointsAfter = totalPointsAfter != null ? totalPointsAfter : 0;
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

    @Override
    public String toString() {
        return "PointsHistory{" +
                "id=" + id +
                ", userId=" + userId +
                ", pointsType=" + pointsType +
                ", points=" + points +
                ", operationDate=" + operationDate +
                ", effectivePointsAfter=" + effectivePointsAfter +
                ", totalPointsAfter=" + totalPointsAfter +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}