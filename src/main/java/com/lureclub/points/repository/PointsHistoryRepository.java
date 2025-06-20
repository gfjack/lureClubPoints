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
 * 积分历史数据访问接口（完整修复版）
 *
 * @author system
 * @date 2025-06-19
 */
@Repository
public interface PointsHistoryRepository extends JpaRepository<PointsHistory, Long> {

    /**
     * 根据用户ID查找积分历史（按日期倒序）
     */
    @Query("SELECT ph FROM PointsHistory ph WHERE ph.userId = :userId ORDER BY ph.operationDate DESC, ph.createTime DESC")
    List<PointsHistory> findByUserIdOrderByOperationDateDescCreateTimeDesc(@Param("userId") Long userId);

    /**
     * 根据用户ID和积分类型查找积分历史
     */
    List<PointsHistory> findByUserIdAndPointsTypeOrderByOperationDateDesc(Long userId, PointsType pointsType);

    /**
     * 计算用户总积分（所有获得积分的总和）
     */
    @Query("SELECT COALESCE(SUM(ph.points), 0) FROM PointsHistory ph WHERE ph.userId = :userId AND ph.points > 0")
    Integer calculateTotalPoints(@Param("userId") Long userId);

    /**
     * 修复：新增计算用户有效积分的方法
     * 有效积分 = 历史获得积分总和 - 历史抵扣积分总和（截止到指定日期前）
     */
    @Query("SELECT COALESCE(" +
            "(SELECT SUM(ph1.points) FROM PointsHistory ph1 WHERE ph1.userId = :userId AND ph1.points > 0 AND ph1.operationDate < :beforeDate) - " +
            "(SELECT SUM(ABS(ph2.points)) FROM PointsHistory ph2 WHERE ph2.userId = :userId AND ph2.points < 0 AND ph2.operationDate < :beforeDate), 0)")
    Integer calculateEffectivePoints(@Param("userId") Long userId, @Param("beforeDate") LocalDate beforeDate);

    /**
     * 计算用户总抵扣积分
     */
    @Query("SELECT COALESCE(SUM(ABS(ph.points)), 0) FROM PointsHistory ph WHERE ph.userId = :userId AND ph.points < 0")
    Integer calculateTotalDeductedPoints(@Param("userId") Long userId);

    /**
     * 获取用户最新的积分历史记录
     */
    @Query("SELECT ph FROM PointsHistory ph WHERE ph.userId = :userId ORDER BY ph.operationDate DESC, ph.createTime DESC LIMIT 1")
    PointsHistory findLatestByUserId(@Param("userId") Long userId);

    /**
     * 获取指定日期范围内所有用户的积分历史
     */
    @Query("SELECT ph FROM PointsHistory ph WHERE ph.operationDate BETWEEN :startDate AND :endDate ORDER BY ph.userId, ph.operationDate DESC")
    List<PointsHistory> findAllByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * 获取指定日期范围内指定类型的积分历史
     */
    @Query("SELECT ph FROM PointsHistory ph WHERE ph.operationDate BETWEEN :startDate AND :endDate " +
            "AND ph.pointsType = :pointsType ORDER BY ph.operationDate DESC")
    List<PointsHistory> findByDateRangeAndType(@Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate,
                                               @Param("pointsType") PointsType pointsType);

    /**
     * 获取当日积分排行榜数据
     */
    @Query("SELECT ph.userId, COALESCE(SUM(ph.points), 0) as dailyPoints FROM PointsHistory ph " +
            "WHERE ph.operationDate = :date AND ph.pointsType = 'EARNED' " +
            "GROUP BY ph.userId ORDER BY dailyPoints DESC")
    List<Object[]> getDailyRanking(@Param("date") LocalDate date);

    /**
     * 获取周积分排行榜数据
     */
    @Query("SELECT ph.userId, COALESCE(SUM(ph.points), 0) as weeklyPoints FROM PointsHistory ph " +
            "WHERE ph.operationDate BETWEEN :startDate AND :endDate AND ph.pointsType = 'EARNED' " +
            "GROUP BY ph.userId ORDER BY weeklyPoints DESC")
    List<Object[]> getWeeklyRanking(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * 获取总积分排行榜数据
     */
    @Query("SELECT ph.userId, COALESCE(SUM(ph.points), 0) as totalPoints FROM PointsHistory ph " +
            "WHERE ph.pointsType = 'EARNED' GROUP BY ph.userId ORDER BY totalPoints DESC")
    List<Object[]> getTotalRanking();

    /**
     * 获取用户指定日期之前的积分历史
     */
    @Query("SELECT ph FROM PointsHistory ph WHERE ph.userId = :userId AND ph.operationDate < :beforeDate ORDER BY ph.operationDate DESC")
    List<PointsHistory> findByUserIdAndOperationDateBefore(@Param("userId") Long userId, @Param("beforeDate") LocalDate beforeDate);

    /**
     * 检查用户在指定日期是否有积分记录
     */
    boolean existsByUserIdAndOperationDate(Long userId, LocalDate date);
}