package com.lureclub.points.repository;

import com.lureclub.points.entity.points.Points;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 积分数据访问接口（最终修复版）
 *
 * @author system
 * @date 2025-06-19
 */
@Repository
public interface PointsRepository extends JpaRepository<Points, Long> {

    /**
     * 根据用户ID查找积分记录（修复：使用标准JPA方法名）
     */
    Points findByUserId(Long userId);

    /**
     * 根据用户ID查找积分记录（Optional版本）
     */
    Optional<Points> findOptionalByUserId(Long userId);

    /**
     * 查找所有积分记录
     */
    @Query("SELECT p FROM Points p ORDER BY p.userId")
    List<Points> findAllOrderByUserId();

    /**
     * 查找需要处理日期变更的积分记录
     */
    @Query("SELECT p FROM Points p WHERE p.todayPoints > 0 AND " +
            "(p.lastPointsDate IS NULL OR p.lastPointsDate < :today)")
    List<Points> findPointsNeedDateProcessing(@Param("today") LocalDate today);

    /**
     * 检查用户是否有积分记录
     */
    boolean existsByUserId(Long userId);

    /**
     * 查找有效积分大于指定值的用户
     */
    @Query("SELECT p FROM Points p WHERE p.effectivePoints >= :minPoints ORDER BY p.effectivePoints DESC")
    List<Points> findByEffectivePointsGreaterThanEqual(@Param("minPoints") Integer minPoints);

    /**
     * 统计总的有效积分
     */
    @Query("SELECT COALESCE(SUM(p.effectivePoints), 0) FROM Points p")
    Long sumAllEffectivePoints();

    /**
     * 统计总的当日积分
     */
    @Query("SELECT COALESCE(SUM(p.todayPoints), 0) FROM Points p")
    Long sumAllTodayPoints();

    /**
     * 查找指定日期范围内最后更新的积分记录
     */
    @Query("SELECT p FROM Points p WHERE p.lastPointsDate BETWEEN :startDate AND :endDate")
    List<Points> findByLastPointsDateBetween(@Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);

    /**
     * 修复：移除批量更新方法（在实体层面处理更安全）
     * 批量更新在JPA中可能导致二级缓存问题，改为在Service层逐个处理
     */

    /**
     * 查找今日有积分变动的用户
     */
    @Query("SELECT p FROM Points p WHERE p.lastPointsDate = :today AND p.todayPoints > 0")
    List<Points> findTodayActiveUsers(@Param("today") LocalDate today);

    /**
     * 获取积分排行榜数据（按有效积分）
     */
    @Query("SELECT p FROM Points p WHERE p.effectivePoints > 0 ORDER BY p.effectivePoints DESC")
    List<Points> findTopUsersByEffectivePoints();

    /**
     * 获取积分排行榜数据（按当前总积分）
     */
    @Query("SELECT p FROM Points p ORDER BY (p.effectivePoints + p.todayPoints) DESC")
    List<Points> findAllOrderByCurrentTotalPoints();

    /**
     * 修复：新增方法 - 查找指定用户ID列表的积分记录
     */
    @Query("SELECT p FROM Points p WHERE p.userId IN :userIds")
    List<Points> findByUserIdIn(@Param("userIds") List<Long> userIds);

    /**
     * 修复：新增方法 - 查找有积分的用户数量
     */
    @Query("SELECT COUNT(p) FROM Points p WHERE p.effectivePoints > 0 OR p.todayPoints > 0")
    Long countUsersWithPoints();

    /**
     * 修复：新增方法 - 查找指定日期的积分记录
     */
    @Query("SELECT p FROM Points p WHERE p.lastPointsDate = :date")
    List<Points> findByLastPointsDate(@Param("date") LocalDate date);
}