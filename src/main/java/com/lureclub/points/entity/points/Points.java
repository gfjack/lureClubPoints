package com.lureclub.points.entity.points;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 积分实体类（最终修复版）
 *
 * @author system
 * @date 2025-06-19
 */
@Entity
@Table(name = "points")
public class Points {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 当日积分
     */
    @Column(name = "today_points", nullable = false)
    private Integer todayPoints = 0;

    /**
     * 有效积分（修复：确保数据库有此字段）
     */
    @Column(name = "effective_points", nullable = false)
    private Integer effectivePoints = 0;

    /**
     * 最后积分更新日期
     */
    @Column(name = "last_points_date")
    private LocalDate lastPointsDate;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    // 构造函数
    public Points() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.lastPointsDate = LocalDate.now();
    }

    public Points(Long userId) {
        this();
        this.userId = userId;
    }

    // 业务方法

    /**
     * 处理日期变更 - 将当日积分转为有效积分
     */
    public boolean processDateChange() {
        LocalDate today = LocalDate.now();

        // 如果最后更新日期不是今天，且有当日积分，则转换
        if (lastPointsDate != null && !lastPointsDate.equals(today) && todayPoints > 0) {
            effectivePoints += todayPoints;
            todayPoints = 0;
            lastPointsDate = today;
            updateTime = LocalDateTime.now();
            return true;
        }

        // 如果是第一次设置或日期为空
        if (lastPointsDate == null) {
            lastPointsDate = today;
            updateTime = LocalDateTime.now();
            return true;
        }

        return false;
    }

    /**
     * 录入当日积分
     */
    public void addTodayPoints(Integer points) {
        if (points != null && points > 0) {
            this.todayPoints += points;
            this.lastPointsDate = LocalDate.now();
            this.updateTime = LocalDateTime.now();
        }
    }

    /**
     * 抵扣有效积分
     */
    public void deductEffectivePoints(Integer points) {
        if (points == null || points <= 0) {
            throw new IllegalArgumentException("抵扣积分必须大于0");
        }

        if (effectivePoints < points) {
            throw new IllegalArgumentException(
                    String.format("有效积分不足，当前有效积分: %d，需要抵扣: %d", effectivePoints, points)
            );
        }

        this.effectivePoints -= points;
        this.updateTime = LocalDateTime.now();
    }

    /**
     * 检查是否可以抵扣指定积分
     */
    public boolean canDeduct(Integer points) {
        return points != null && points > 0 && effectivePoints >= points;
    }

    /**
     * 调整有效积分（管理员操作）
     */
    public void adjustEffectivePoints(Integer points) {
        if (points != null) {
            this.effectivePoints = Math.max(0, this.effectivePoints + points);
            this.updateTime = LocalDateTime.now();
        }
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

    public Integer getTodayPoints() {
        return todayPoints;
    }

    public void setTodayPoints(Integer todayPoints) {
        this.todayPoints = todayPoints != null ? todayPoints : 0;
    }

    public Integer getEffectivePoints() {
        return effectivePoints;
    }

    public void setEffectivePoints(Integer effectivePoints) {
        this.effectivePoints = effectivePoints != null ? effectivePoints : 0;
    }

    public LocalDate getLastPointsDate() {
        return lastPointsDate;
    }

    public void setLastPointsDate(LocalDate lastPointsDate) {
        this.lastPointsDate = lastPointsDate;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @PreUpdate
    public void preUpdate() {
        this.updateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Points{" +
                "id=" + id +
                ", userId=" + userId +
                ", todayPoints=" + todayPoints +
                ", effectivePoints=" + effectivePoints +
                ", lastPointsDate=" + lastPointsDate +
                '}';
    }
}