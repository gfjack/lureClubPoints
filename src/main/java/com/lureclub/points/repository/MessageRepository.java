package com.lureclub.points.repository;

import com.lureclub.points.entity.message.Message;
import com.lureclub.points.enums.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 留言数据访问接口
 *
 * @author system
 * @date 2025-06-19
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * 查找所有公开的留言（用户端）
     *
     * @return 留言列表
     */
    @Query("SELECT m FROM Message m WHERE m.isDeleted = 0 AND (m.status = 'PUBLISHED' OR m.status = 'REPLIED') ORDER BY m.createTime DESC")
    List<Message> findPublicMessages();

    /**
     * 根据ID和删除状态查找留言
     *
     * @param id 留言ID
     * @param isDeleted 删除状态
     * @return 留言
     */
    Message findByIdAndIsDeleted(Long id, Integer isDeleted);

    /**
     * 查找所有未删除的留言，按创建时间倒序（管理员用）
     *
     * @return 留言列表
     */
    @Query("SELECT m FROM Message m WHERE m.isDeleted = 0 ORDER BY m.createTime DESC")
    List<Message> findAllActiveOrderByCreateTime();

    /**
     * 根据用户ID查找留言
     *
     * @param userId 用户ID
     * @return 留言列表
     */
    @Query("SELECT m FROM Message m WHERE m.userId = :userId AND m.isDeleted = 0 ORDER BY m.createTime DESC")
    List<Message> findByUserIdAndNotDeleted(@Param("userId") Long userId);

    /**
     * 根据状态查找留言
     *
     * @param status 留言状态
     * @return 留言列表
     */
    @Query("SELECT m FROM Message m WHERE m.status = :status AND m.isDeleted = 0 ORDER BY m.createTime DESC")
    List<Message> findByStatusAndNotDeleted(@Param("status") MessageStatus status);

}