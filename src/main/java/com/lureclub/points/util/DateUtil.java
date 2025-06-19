package com.lureclub.points.util;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * 日期工具类
 *
 * @author system
 * @date 2025-06-19
 */
@Component
public class DateUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取当前日期
     *
     * @return 当前日期
     */
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    /**
     * 获取当前日期时间
     *
     * @return 当前日期时间
     */
    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 获取本周开始日期（周一）
     *
     * @return 本周开始日期
     */
    public LocalDate getWeekStart() {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    /**
     * 获取本周结束日期（周日）
     *
     * @return 本周结束日期
     */
    public LocalDate getWeekEnd() {
        return LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    }

    /**
     * 获取本月开始日期
     *
     * @return 本月开始日期
     */
    public LocalDate getMonthStart() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获取本月结束日期
     *
     * @return 本月结束日期
     */
    public LocalDate getMonthEnd() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 格式化日期
     *
     * @param date 日期
     * @return 格式化后的日期字符串
     */
    public String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : null;
    }

    /**
     * 格式化日期时间
     *
     * @param dateTime 日期时间
     * @return 格式化后的日期时间字符串
     */
    public String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATETIME_FORMATTER) : null;
    }

    /**
     * 解析日期字符串
     *
     * @param dateStr 日期字符串
     * @return 日期
     */
    public LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 检查是否是今天
     *
     * @param date 日期
     * @return 是否是今天
     */
    public boolean isToday(LocalDate date) {
        return date != null && date.equals(LocalDate.now());
    }

    /**
     * 检查是否是本周
     *
     * @param date 日期
     * @return 是否是本周
     */
    public boolean isThisWeek(LocalDate date) {
        if (date == null) {
            return false;
        }
        LocalDate weekStart = getWeekStart();
        LocalDate weekEnd = getWeekEnd();
        return !date.isBefore(weekStart) && !date.isAfter(weekEnd);
    }

    /**
     * 计算两个日期之间的天数差
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 天数差
     */
    public long daysBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
    }

}