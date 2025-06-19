package com.lureclub.points.task;

import com.lureclub.points.entity.points.Points;
import com.lureclub.points.repository.PointsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * 积分转换定时任务
 * 每日自动将所有用户的当日积分转为有效积分
 *
 * @author system
 * @date 2025-06-19
 */
@Component
public class PointsConversionTask {

    private static final Logger logger = LoggerFactory.getLogger(PointsConversionTask.class);

    @Autowired
    private PointsRepository pointsRepository;

    /**
     * 每天凌晨1点执行积分转换任务
     * 将前一日的当日积分转为有效积分
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void convertDailyPoints() {
        logger.info("开始执行积分转换定时任务");

        try {
            LocalDate today = LocalDate.now();

            // 查找需要处理日期变更的积分记录
            List<Points> pointsList = pointsRepository.findPointsNeedDateProcessing(today);

            int processedCount = 0;
            for (Points points : pointsList) {
                try {
                    // 处理日期变更，将当日积分转为有效积分
                    points.processDateChange();
                    pointsRepository.save(points);
                    processedCount++;
                } catch (Exception e) {
                    logger.error("处理用户{}的积分转换失败: {}", points.getUserId(), e.getMessage(), e);
                }
            }

            logger.info("积分转换定时任务执行完成，共处理{}条记录", processedCount);

        } catch (Exception e) {
            logger.error("积分转换定时任务执行失败: {}", e.getMessage(), e);
        }
    }

}