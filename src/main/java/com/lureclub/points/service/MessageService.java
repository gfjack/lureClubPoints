package com.lureclub.points.service;

import com.lureclub.points.entity.message.vo.request.MessageCreateVo;
import com.lureclub.points.entity.message.vo.request.MessageReplyVo;
import com.lureclub.points.entity.message.vo.response.MessageVo;

import java.util.List;

/**
 * 留言服务接口
 *
 * @author system
 * @date 2025-06-19
 */
public interface MessageService {

    /**
     * 获取所有公开留言（用户端）
     *
     * @return 留言列表
     */
    List<MessageVo> getAllPublicMessages();

    /**
     * 创建留言（用户）
     *
     * @param createVo 留言内容
     * @return 留言信息
     */
    MessageVo createMessage(MessageCreateVo createVo);

    /**
     * 获取所有留言（管理员）
     *
     * @return 留言列表
     */
    List<MessageVo> getAllMessagesForAdmin();

    /**
     * 回复留言（管理员）
     *
     * @param messageId 留言ID
     * @param replyVo 回复内容
     * @return 留言信息
     */
    MessageVo replyMessage(Long messageId, MessageReplyVo replyVo);

    /**
     * 设置留言可见性（管理员）
     *
     * @param messageId 留言ID
     * @param isVisible 是否可见
     * @return 留言信息
     */
    MessageVo setMessageVisibility(Long messageId, Boolean isVisible);

}