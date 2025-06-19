package com.lureclub.points.service.impl;

import com.lureclub.points.entity.message.Message;
import com.lureclub.points.entity.message.MessageReply;
import com.lureclub.points.entity.message.vo.request.MessageCreateVo;
import com.lureclub.points.entity.message.vo.request.MessageReplyVo;
import com.lureclub.points.entity.message.vo.response.MessageVo;
import com.lureclub.points.entity.user.User;
import com.lureclub.points.enums.MessageStatus;
import com.lureclub.points.exception.BusinessException;
import com.lureclub.points.repository.MessageRepository;
import com.lureclub.points.repository.MessageReplyRepository;
import com.lureclub.points.repository.UserRepository;
import com.lureclub.points.service.MessageService;
import com.lureclub.points.util.ValidationUtil;
import com.lureclub.points.entity.message.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 留言服务实现类
 *
 * @author system
 * @date 2025-06-19
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageReplyRepository messageReplyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationUtil validationUtil;

    @Autowired
    private MessageConverter messageConverter;

    /**
     * 获取所有公开留言实现（用户端）
     */
    @Override
    public List<MessageVo> getAllPublicMessages() {
        List<Message> messages = messageRepository.findPublicMessages();
        return convertToMessageVoList(messages, true);
    }

    /**
     * 创建留言实现（用户）
     */
    @Override
    @Transactional
    public MessageVo createMessage(MessageCreateVo createVo) {
        Long userId = getCurrentUserId();

        // 验证留言内容长度
        if (createVo.getContent() == null || createVo.getContent().trim().isEmpty()) {
            throw new BusinessException("留言内容不能为空");
        }

        if (createVo.getContent().length() > 1000) {
            throw new BusinessException("留言内容不能超过1000个字符");
        }

        Message message = new Message(userId, createVo.getContent().trim());
        message = messageRepository.save(message);

        User user = userRepository.findById(userId).orElse(null);
        String username = user != null ? user.getUsername() : "未知用户";

        return messageConverter.toMessageVo(message, username, null);
    }

    /**
     * 获取所有留言实现（管理员）
     */
    @Override
    public List<MessageVo> getAllMessagesForAdmin() {
        List<Message> messages = messageRepository.findAllActiveOrderByCreateTime();
        return convertToMessageVoList(messages, false);
    }

    /**
     * 回复留言实现（管理员）
     */
    @Override
    @Transactional
    public MessageVo replyMessage(Long messageId, MessageReplyVo replyVo) {
        Message message = messageRepository.findByIdAndIsDeleted(messageId, 0);
        if (message == null) {
            throw new BusinessException("留言不存在");
        }

        // 创建回复
        MessageReply reply = new MessageReply(messageId, replyVo.getContent(), replyVo.getIsVisible());
        messageReplyRepository.save(reply);

        // 更新留言状态
        message.setStatus(MessageStatus.REPLIED);
        messageRepository.save(message);

        return getMessageVo(message);
    }

    /**
     * 设置留言可见性实现（管理员）
     */
    @Override
    @Transactional
    public MessageVo setMessageVisibility(Long messageId, Boolean isVisible) {
        Message message = messageRepository.findByIdAndIsDeleted(messageId, 0);
        if (message == null) {
            throw new BusinessException("留言不存在");
        }

        // 根据可见性设置状态
        if (Boolean.TRUE.equals(isVisible)) {
            if (message.getStatus() == MessageStatus.PENDING) {
                message.setStatus(MessageStatus.PUBLISHED);
            }
        } else {
            message.setStatus(MessageStatus.HIDDEN);
        }

        messageRepository.save(message);

        return getMessageVo(message);
    }

    /**
     * 转换为MessageVo列表
     */
    private List<MessageVo> convertToMessageVoList(List<Message> messages, boolean onlyVisibleReplies) {
        if (messages.isEmpty()) {
            return List.of();
        }

        // 获取所有留言的回复
        List<Long> messageIds = messages.stream().map(Message::getId).collect(Collectors.toList());
        List<MessageReply> allReplies = onlyVisibleReplies
                ? messageReplyRepository.findVisibleRepliesByMessageIds(messageIds)
                : messageReplyRepository.findAllRepliesByMessageIds(messageIds);

        Map<Long, List<MessageReply>> repliesMap = allReplies.stream()
                .collect(Collectors.groupingBy(MessageReply::getMessageId));

        // 获取用户信息
        List<Long> userIds = messages.stream().map(Message::getUserId).distinct().collect(Collectors.toList());
        Map<Long, User> userMap = userRepository.findAllById(userIds).stream()
                .collect(Collectors.toMap(User::getId, user -> user));

        return messages.stream()
                .map(message -> {
                    User user = userMap.get(message.getUserId());
                    String username = user != null ? user.getUsername() : "未知用户";

                    List<com.lureclub.points.entity.message.vo.response.MessageReplyVo> replyVos = repliesMap.getOrDefault(message.getId(), List.of())
                            .stream()
                            .map(messageConverter::toMessageReplyVo)
                            .collect(Collectors.toList());

                    return messageConverter.toMessageVo(message, username, replyVos);
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取单个MessageVo
     */
    private MessageVo getMessageVo(Message message) {
        User user = userRepository.findById(message.getUserId()).orElse(null);
        String username = user != null ? user.getUsername() : "未知用户";

        List<MessageReply> replies = messageReplyRepository.findAllByMessageIdAndIsDeleted(message.getId(), 0);
        List<com.lureclub.points.entity.message.vo.response.MessageReplyVo> replyVos = replies.stream()
                .map(messageConverter::toMessageReplyVo)
                .collect(Collectors.toList());

        return messageConverter.toMessageVo(message, username, replyVos);
    }

    /**
     * 获取当前登录用户ID
     */
    private Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Long) {
            return (Long) principal;
        }
        throw new BusinessException("用户未登录");
    }

}