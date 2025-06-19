package com.lureclub.points.controller.user;

import com.lureclub.points.api.user.UserMessageApi;
import com.lureclub.points.entity.message.vo.request.MessageCreateVo;
import com.lureclub.points.entity.message.vo.response.MessageVo;
import com.lureclub.points.entity.common.ApiResponse;
import com.lureclub.points.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 用户留言控制器实现
 *
 * @author system
 * @date 2025-06-19
 */
@RestController
public class UserMessageController implements UserMessageApi {

    @Autowired
    private MessageService messageService;

    /**
     * 查看所有留言实现
     */
    @Override
    public ApiResponse<List<MessageVo>> getAllMessages() {
        try {
            List<MessageVo> result = messageService.getAllPublicMessages();
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("获取留言列表失败: " + e.getMessage());
        }
    }

    /**
     * 用户上传留言实现
     */
    @Override
    public ApiResponse<MessageVo> createMessage(@Valid MessageCreateVo createVo) {
        try {
            MessageVo result = messageService.createMessage(createVo);
            return ApiResponse.success(result, "留言发布成功，等待审核");
        } catch (Exception e) {
            return ApiResponse.error("发布留言失败: " + e.getMessage());
        }
    }

}