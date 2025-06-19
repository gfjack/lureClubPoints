package com.lureclub.points.repository;

import com.lureclub.points.entity.points.Points;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * 积分数据访问接口
 *
 * @author system
 * @date 2025-06-19
 */
@Repository
public interface PointsRepository extends JpaRepository<Points, Long> {

    /**
     * 根据用户ID查找积分记录
     *
     * @param userId 用户ID
     * @return 积分记录
     */
    Points findByUserId(Long userId);

    /**
     * 查找所有用户的积分记录
     *
     * @return 积分记录列表
     */
    @Query("SELECT p FROM Points p ORDER BY p.userId")
    List<Points> findAllOrderByUserId();

    /**
     * 查找需要处理日期变更的积分记录
     *
     * @param today 今天日期
     * @return 需要处理的积分记录列表
     */
    @Query("SELECT p FROM Points p WHERE p.lastPointsDate IS NULL OR p.lastPointsDate < :today")
    List<Points> findPointsNeedDateProcessing(@Param("today") LocalDate today);

    /**
     * 检查用户是否有积分记录
     *
     * @param userId 用户ID
     * @return 是否存在
     */
    boolean existsByUserId(Long userId);

}