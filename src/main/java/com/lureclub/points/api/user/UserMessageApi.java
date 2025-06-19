package com.lureclub.points.api.user;

import com.lureclub.points.entity.message.vo.request.MessageCreateVo;
import com.lureclub.points.entity.message.vo.response.MessageVo;
import com.lureclub.points.entity.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 用户留言API接口
 *
 * @author system
 * @date 2025-06-19
 */
@Tag(name = "用户留言接口", description = "用户留言板相关接口")
@RequestMapping("/api/user/message")
public interface UserMessageApi {

    /**
     * 查看所有留言
     *
     * @return 留言列表
     */
    @Operation(summary = "查看所有留言", description = "查看留言板系统所有留言，包含管理员回复")
    @GetMapping("")
    ApiResponse<List<MessageVo>> getAllMessages();

    /**
     * 用户上传留言
     *
     * @param createVo 留言内容
     * @return 创建结果
     */
    @Operation(summary = "上传留言", description = "用户上传新的留言到留言板")
    @PostMapping("")
    ApiResponse<MessageVo> createMessage(@Valid @RequestBody MessageCreateVo createVo);

}