package com.lureclub.points.entity.points;

import com.lureclub.points.enums.PointsType;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 积分历史实体类
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