package com.lureclub.points.repository;

import com.lureclub.points.entity.points.PointsHistory;
import com.lureclub.points.enums.PointsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * 积分历史数据访问接口
 *
 * @author system
 * @date 2025-06-19
 */
@Repository
public interface PointsHistoryRepository extends JpaRepository<PointsHistory, Long> {

    /**
     * 根据用户ID查找积分历史（按日期倒序）
     *
     * @param userId 用户ID
     * @return 积分历史列表
     */
    @Query("SELECT ph FROM PointsHistory ph WHERE ph.userId = :userId ORDER BY ph.operationDate DESC, ph.createTime DESC")
    List<PointsHistory> findByUserIdOrderByOperationDateDescCreateTimeDesc(@Param("userId") Long userId);

    /**
     * 根据用户ID和积分类型查找积分历史
     *
     * @param userId 用户ID
     * @param pointsType 积分类型
     * @return 积分历史列表
     */
    List<PointsHistory> findByUserIdAndPointsTypeOrderByOperationDateDesc(Long userId, PointsType pointsType);

    /**
     * 计算用户总积分（所有获得积分的总和）
     *
     * @param userId 用户ID
     * @return 总积分
     */
    @Query("SELECT COALESCE(SUM(ph.points), 0) FROM PointsHistory ph WHERE ph.userId = :userId AND ph.points > 0")
    Integer calculateTotalPoints(@Param("userId") Long userId);

    /**
     * 计算用户有效积分（获得积分 - 抵扣积分，排除当日积分）
     *
     * @param userId 用户ID
     * @param today 今天日期
     * @return 有效积分
     */
    @Query("SELECT COALESCE(SUM(ph.points), 0) FROM PointsHistory ph WHERE ph.userId = :userId AND ph.operationDate < :today")
    Integer calculateEffectivePoints(@Param("userId") Long userId, @Param("today") LocalDate today);

    /**
     * 获取指定日期范围内所有用户的积分历史
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 积分历史列表
     */
    @Query("SELECT ph FROM PointsHistory ph WHERE ph.operationDate BETWEEN :startDate AND :endDate ORDER BY ph.userId, ph.operationDate DESC")
    List<PointsHistory> findAllByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}