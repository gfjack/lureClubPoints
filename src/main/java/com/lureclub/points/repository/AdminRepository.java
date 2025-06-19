package com.lureclub.points.repository;

import com.lureclub.points.entity.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 管理员数据访问接口
 *
 * @author system
 * @date 2025-06-19
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    /**
     * 根据用户名查找管理员
     *
     * @param username 用户名
     * @return 管理员信息
     */
    Admin findByUsername(String username);

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 查找所有未删除的管理员
     *
     * @return 管理员列表
     */
    @Query("SELECT a FROM Admin a WHERE a.isDeleted = 0 ORDER BY a.createTime DESC")
    List<Admin> findAllActive();

    /**
     * 根据ID查找未删除的管理员
     *
     * @param id 管理员ID
     * @return 管理员信息
     */
    @Query("SELECT a FROM Admin a WHERE a.id = :id AND a.isDeleted = 0")
    Optional<Admin> findActiveAdminById(Long id);

    /**
     * 查找所有启用的管理员
     *
     * @return 管理员列表
     */
    @Query("SELECT a FROM Admin a WHERE a.isDeleted = 0 AND a.isEnabled = true ORDER BY a.createTime DESC")
    List<Admin> findAllEnabled();

}