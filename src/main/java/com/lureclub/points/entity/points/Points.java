package com.lureclub.points.entity.points;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 积分实体类（包含有效积分、总积分）
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
    }

    public Points(Long userId) {
        this();
        this.userId = userId;
    }

    // 核心方法

    /**
     * 处理当日积分转换
     * 当日积分次日自动转为有效积分
     */
    public void processDateChange() {
        LocalDate today = LocalDate.now();
        if (lastPointsDate != null && !lastPointsDate.equals(today)) {
            // 前一日积分转为有效积分，当日积分清零
            this.todayPoints = 0;
        }
        this.lastPointsDate = today;
        this.updateTime = LocalDateTime.now();
    }

    /**
     * 录入当日积分
     *
     * @param points 积分数量
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
     *
     * @param points 抵扣积分数量
     */
    public void deductEffectivePoints(Integer points) {
        if (points != null && points > 0) {
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
        this.todayPoints = todayPoints;
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

}