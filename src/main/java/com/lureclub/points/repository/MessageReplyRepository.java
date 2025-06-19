package com.lureclub.points.repository;

import com.lureclub.points.entity.message.MessageReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 留言回复数据访问接口
 *
 * @author system
 * @date 2025-06-19
 */
@Repository
public interface MessageReplyRepository extends JpaRepository<MessageReply, Long> {

    /**
     * 根据留言ID查找所有回复
     *
     * @param messageId 留言ID
     * @param isDeleted 删除状态
     * @return 回复列表
     */
    @Query("SELECT mr FROM MessageReply mr WHERE mr.messageId = :messageId AND mr.isDeleted = :isDeleted ORDER BY mr.createTime ASC")
    List<MessageReply> findAllByMessageIdAndIsDeleted(@Param("messageId") Long messageId, @Param("isDeleted") Integer isDeleted);

    /**
     * 根据留言ID列表查找所有可见回复
     *
     * @param messageIds 留言ID列表
     * @return 回复列表
     */
    @Query("SELECT mr FROM MessageReply mr WHERE mr.messageId IN :messageIds AND mr.isDeleted = 0 AND mr.isVisible = true ORDER BY mr.createTime ASC")
    List<MessageReply> findVisibleRepliesByMessageIds(@Param("messageIds") List<Long> messageIds);

    /**
     * 根据留言ID列表查找所有回复（管理员用）
     *
     * @param messageIds 留言ID列表
     * @return 回复列表
     */
    @Query("SELECT mr FROM MessageReply mr WHERE mr.messageId IN :messageIds AND mr.isDeleted = 0 ORDER BY mr.createTime ASC")
    List<MessageReply> findAllRepliesByMessageIds(@Param("messageIds") List<Long> messageIds);

}