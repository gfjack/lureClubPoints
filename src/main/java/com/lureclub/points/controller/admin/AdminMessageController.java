package com.lureclub.points.controller.admin;

import com.lureclub.points.api.admin.AdminMessageApi;
import com.lureclub.points.entity.message.vo.request.MessageReplyVo;
import com.lureclub.points.entity.message.vo.response.MessageVo;
import com.lureclub.points.entity.common.ApiResponse;
import com.lureclub.points.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 管理员留言控制器实现
 *
 * @author system
 * @date 2025-06-19
 */
@RestController
@RequestMapping("/api/admin/message")
public class AdminMessageController implements AdminMessageApi {

    @Autowired
    private MessageService messageService;

    /**
     * 获取所有留言实现（管理员）
     */
    @Override
    @GetMapping("")
    public ApiResponse<List<MessageVo>> getAllMessages() {
        try {
            List<MessageVo> result = messageService.getAllMessagesForAdmin();
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("获取留言列表失败: " + e.getMessage());
        }
    }

    /**
     * 回复留言实现
     */
    @Override
    @PostMapping("/{messageId}/reply")
    public ApiResponse<MessageVo> replyMessage(Long messageId, @Valid MessageReplyVo replyVo) {
        try {
            MessageVo result = messageService.replyMessage(messageId, replyVo);
            return ApiResponse.success(result, "回复成功");
        } catch (Exception e) {
            return ApiResponse.error("回复留言失败: " + e.getMessage());
        }
    }

    /**
     * 设置留言可见性实现
     */
    @Override
    @PutMapping("/{messageId}/visibility")
    public ApiResponse<MessageVo> setMessageVisibility(Long messageId, Boolean isVisible) {
        try {
            MessageVo result = messageService.setMessageVisibility(messageId, isVisible);
            return ApiResponse.success(result, "设置成功");
        } catch (Exception e) {
            return ApiResponse.error("设置留言可见性失败: " + e.getMessage());
        }
    }

}