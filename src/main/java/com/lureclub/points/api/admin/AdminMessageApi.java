package com.lureclub.points.api.admin;

import com.lureclub.points.entity.message.vo.request.MessageReplyVo;
import com.lureclub.points.entity.message.vo.response.MessageVo;
import com.lureclub.points.entity.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 管理员留言管理API接口
 *
 * @author system
 * @date 2025-06-19
 */
@Tag(name = "管理员留言管理接口", description = "管理员留言管理相关接口")
@RequestMapping("/api/admin/message")
public interface AdminMessageApi {

    /**
     * 获取所有留言（管理员）
     *
     * @return 留言列表
     */
    @Operation(summary = "获取所有留言", description = "管理员获取所有留言列表")
    @GetMapping("")
    ApiResponse<List<MessageVo>> getAllMessages();

    /**
     * 回复留言
     *
     * @param messageId 留言ID
     * @param replyVo 回复内容
     * @return 回复结果
     */
    @Operation(summary = "回复留言", description = "管理员回复用户留言")
    @PostMapping("/{messageId}/reply")
    ApiResponse<MessageVo> replyMessage(@Parameter(description = "留言ID") @PathVariable Long messageId,
                                        @Valid @RequestBody MessageReplyVo replyVo);

    /**
     * 设置留言可见性
     *
     * @param messageId 留言ID
     * @param isVisible 是否可见
     * @return 设置结果
     */
    @Operation(summary = "设置留言可见性", description = "管理员设置留言是否公开可见")
    @PutMapping("/{messageId}/visibility")
    ApiResponse<MessageVo> setMessageVisibility(@Parameter(description = "留言ID") @PathVariable Long messageId,
                                                @Parameter(description = "是否可见") @RequestParam Boolean isVisible);

}