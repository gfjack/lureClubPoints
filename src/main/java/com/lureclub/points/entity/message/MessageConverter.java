package com.lureclub.points.entity.message;

import com.lureclub.points.entity.message.vo.response.MessageVo;
import com.lureclub.points.entity.message.vo.response.MessageReplyVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 留言转换器
 *
 * @author system
 * @date 2025-06-19
 */
@Component
public class MessageConverter {

    /**
     * 将Message实体转换为MessageVo
     *
     * @param message 留言实体
     * @param username 用户名
     * @param replies 回复列表
     * @return 留言VO
     */
    public MessageVo toMessageVo(Message message, String username, List<MessageReplyVo> replies) {
        if (message == null) {
            return null;
        }
        return new MessageVo(
                message.getId(),
                message.getUserId(),
                username,
                message.getContent(),
                message.getStatus(),
                message.getCreateTime(),
                replies
        );
    }

    /**
     * 将MessageReply实体转换为MessageReplyVo
     *
     * @param reply 回复实体
     * @return 回复VO
     */
    public MessageReplyVo toMessageReplyVo(MessageReply reply) {
        if (reply == null) {
            return null;
        }
        return new MessageReplyVo(
                reply.getId(),
                reply.getMessageId(),
                reply.getContent(),
                reply.getIsVisible(),
                reply.getCreateTime()
        );
    }

}