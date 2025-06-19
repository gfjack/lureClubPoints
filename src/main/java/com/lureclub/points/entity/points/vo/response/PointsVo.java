package com.lureclub.points.entity.points.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

/**
 * 积分信息响应VO（含当日积分字段）
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "积分信息")
public class PointsVo {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "当日积分")
    private Integer todayPoints;

    @Schema(description = "有效积分")
    private Integer effectivePoints;

    @Schema(description = "总积分")
    private Integer totalPoints;

    @Schema(description = "最后积分更新日期")
    private LocalDate lastPointsDate;

    // 构造函数
    public PointsVo() {}

    public PointsVo(Long userId, Integer todayPoints, Integer effectivePoints, Integer totalPoints, LocalDate lastPointsDate) {
        this.userId = userId;
        this.todayPoints = todayPoints;
        this.effectivePoints = effectivePoints;
        this.totalPoints = totalPoints;
        this.lastPointsDate = lastPointsDate;
    }

    // Getter和Setter方法
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

    public Integer getEffectivePoints() {
        return effectivePoints;
    }

    public void setEffectivePoints(Integer effectivePoints) {
        this.effectivePoints = effectivePoints;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public LocalDate getLastPointsDate() {
        return lastPointsDate;
    }

    public void setLastPointsDate(LocalDate lastPointsDate) {
        this.lastPointsDate = lastPointsDate;
    }

}