package com.lureclub.points.task;

import com.lureclub.points.service.impl.PointsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 积分转换定时任务（完整修复版）
 *
 * @author system
 * @date 2025-06-19
 */
@Component
@ConditionalOnProperty(name = "points.task.enabled", havingValue = "true", matchIfMissing = true)
public class PointsConversionTask {

    private static final Logger logger = LoggerFactory.getLogger(PointsConversionTask.class);

    @Autowired
    private PointsServiceImpl pointsService;

    /**
     * 每天凌晨1点执行积分转换任务
     */
    @Scheduled(cron = "${points.task.cron:0 0 1 * * ?}")
    public void convertDailyPoints() {
        logger.info("开始执行积分转换定时任务，时间: {}", LocalDateTime.now());

        try {
            int processedCount = pointsService.batchProcessDateChange();

            logger.info("积分转换定时任务执行完成，共处理{}条记录，时间: {}",
                    processedCount, LocalDateTime.now());

            recordTaskExecution(processedCount, true, null);

        } catch (Exception e) {
            logger.error("积分转换定时任务执行失败，时间: {}, 错误: {}",
                    LocalDateTime.now(), e.getMessage(), e);

            recordTaskExecution(0, false, e.getMessage());
        }
    }

    /**
     * 手动触发积分转换任务
     */
    public void manualConvertDailyPoints() {
        logger.info("手动触发积分转换任务，时间: {}", LocalDateTime.now());
        convertDailyPoints();
    }

    /**
     * 每小时检查是否有遗漏的积分转换
     */
    @Scheduled(cron = "${points.check.cron:0 0 * * * ?}")
    public void checkMissedConversion() {
        logger.debug("开始执行积分转换检查任务，时间: {}", LocalDateTime.now());

        try {
            int processedCount = pointsService.batchProcessDateChange();

            if (processedCount > 0) {
                logger.info("积分转换检查任务发现并处理了{}条遗漏记录，时间: {}",
                        processedCount, LocalDateTime.now());

                recordCheckExecution(processedCount, true, null);
            } else {
                logger.debug("积分转换检查任务完成，无遗漏记录，时间: {}", LocalDateTime.now());
            }

        } catch (Exception e) {
            logger.error("积分转换检查任务执行失败，时间: {}, 错误: {}",
                    LocalDateTime.now(), e.getMessage(), e);

            recordCheckExecution(0, false, e.getMessage());
        }
    }

    /**
     * 记录主任务执行情况
     */
    private void recordTaskExecution(int processedCount, boolean success, String errorMessage) {
        try {
            if (success) {
                logger.info("定时任务执行成功记录 - 处理数量: {}, 时间: {}",
                        processedCount, LocalDateTime.now());
            } else {
                logger.error("定时任务执行失败记录 - 错误信息: {}, 时间: {}",
                        errorMessage, LocalDateTime.now());
            }
        } catch (Exception e) {
            logger.error("记录任务执行情况失败: {}", e.getMessage());
        }
    }

    /**
     * 记录检查任务执行情况
     */
    private void recordCheckExecution(int processedCount, boolean success, String errorMessage) {
        try {
            if (success && processedCount > 0) {
                logger.info("检查任务执行成功记录 - 处理数量: {}, 时间: {}",
                        processedCount, LocalDateTime.now());
            } else if (!success) {
                logger.error("检查任务执行失败记录 - 错误信息: {}, 时间: {}",
                        errorMessage, LocalDateTime.now());
            }
        } catch (Exception e) {
            logger.error("记录检查任务执行情况失败: {}", e.getMessage());
        }
    }

    /**
     * 获取任务执行状态
     */
    public TaskStatus getTaskStatus() {
        return new TaskStatus(
                LocalDateTime.now(),
                "积分转换任务",
                true,
                "正常运行"
        );
    }

    /**
     * 任务状态内部类
     */
    public static class TaskStatus {
        private final LocalDateTime lastCheckTime;
        private final String taskName;
        private final boolean isHealthy;
        private final String status;

        public TaskStatus(LocalDateTime lastCheckTime, String taskName, boolean isHealthy, String status) {
            this.lastCheckTime = lastCheckTime;
            this.taskName = taskName;
            this.isHealthy = isHealthy;
            this.status = status;
        }

        public LocalDateTime getLastCheckTime() { return lastCheckTime; }
        public String getTaskName() { return taskName; }
        public boolean isHealthy() { return isHealthy; }
        public String getStatus() { return status; }

        @Override
        public String toString() {
            return String.format("TaskStatus{taskName='%s', isHealthy=%s, status='%s', lastCheckTime=%s}",
                    taskName, isHealthy, status, lastCheckTime);
        }
    }
}