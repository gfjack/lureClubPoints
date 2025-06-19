package com.lureclub.points.entity.points.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 积分搜索请求VO（积分搜索VO）
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "积分搜索请求参数")
public class PointsSearchVo {

    @Schema(description = "用户ID", example = "1")
    private Long userId;

    @Schema(description = "用户名关键词", example = "test")
    private String username;

    @Schema(description = "开始日期", example = "2025-06-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "结束日期", example = "2025-06-30")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Schema(description = "最小积分", example = "10")
    private Integer minPoints;

    @Schema(description = "最大积分", example = "100")
    private Integer maxPoints;

    // 构造函数
    public PointsSearchVo() {}

    public PointsSearchVo(Long userId, String username, LocalDate startDate, LocalDate endDate,
                          Integer minPoints, Integer maxPoints) {
        this.userId = userId;
        this.username = username;
        this.startDate = startDate;
        this.endDate = endDate;
        this.minPoints = minPoints;
        this.maxPoints = maxPoints;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getMinPoints() {
        return minPoints;
    }

    public void setMinPoints(Integer minPoints) {
        this.minPoints = minPoints;
    }

    public Integer getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(Integer maxPoints) {
        this.maxPoints = maxPoints;
    }

}