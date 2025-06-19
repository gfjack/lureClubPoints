package com.lureclub.points.repository;

import com.lureclub.points.entity.prize.Prize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 奖品数据访问接口
 *
 * @author system
 * @date 2025-06-19
 */
@Repository
public interface PrizeRepository extends JpaRepository<Prize, Long> {

    /**
     * 查找所有启用且未删除的奖品，按排序序号排序
     *
     * @return 奖品列表
     */
    @Query("SELECT p FROM Prize p WHERE p.isDeleted = 0 AND p.isEnabled = true ORDER BY p.sortOrder ASC, p.createTime DESC")
    List<Prize> findAllActiveOrderBySortOrder();

    /**
     * 根据ID、删除状态和启用状态查找奖品
     *
     * @param id 奖品ID
     * @param isDeleted 删除状态
     * @param isEnabled 启用状态
     * @return 奖品
     */
    Prize findByIdAndIsDeletedAndIsEnabled(Long id, Integer isDeleted, Boolean isEnabled);

    /**
     * 根据ID和删除状态查找奖品
     *
     * @param id 奖品ID
     * @param isDeleted 删除状态
     * @return 奖品
     */
    Prize findByIdAndIsDeleted(Long id, Integer isDeleted);

    /**
     * 查找所有未删除的奖品（管理员用）
     *
     * @return 奖品列表
     */
    @Query("SELECT p FROM Prize p WHERE p.isDeleted = 0 ORDER BY p.sortOrder ASC, p.createTime DESC")
    List<Prize> findAllActive();

    /**
     * 根据名称查找奖品（用于重名检查）
     *
     * @param name 奖品名称
     * @return 奖品列表
     */
    @Query("SELECT p FROM Prize p WHERE p.name = :name AND p.isDeleted = 0")
    List<Prize> findByNameAndNotDeleted(@Param("name") String name);

}