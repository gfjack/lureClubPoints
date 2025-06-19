package com.lureclub.points.entity.points.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 积分详情响应VO（积分详情VO）
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "积分详情信息")
public class PointsDetailVo {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "当日积分")
    private Integer todayPoints;

    @Schema(description = "有效积分")
    private Integer effectivePoints;

    @Schema(description = "总积分")
    private Integer totalPoints;

    @Schema(description = "最后积分更新日期")
    private LocalDate lastPointsDate;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // 构造函数
    public PointsDetailVo() {}

    public PointsDetailVo(Long userId, String username, String phone, Integer todayPoints,
                          Integer effectivePoints, Integer totalPoints, LocalDate lastPointsDate,
                          LocalDateTime createTime) {
        this.userId = userId;
        this.username = username;
        this.phone = phone;
        this.todayPoints = todayPoints;
        this.effectivePoints = effectivePoints;
        this.totalPoints = totalPoints;
        this.lastPointsDate = lastPointsDate;
        this.createTime = createTime;
    }

    // Getter和Setter方法
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

}