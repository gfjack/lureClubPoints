package com.lureclub.points.api.user;

import com.lureclub.points.entity.announcement.vo.request.AnnouncementSearchVo;
import com.lureclub.points.entity.announcement.vo.response.AnnouncementVo;
import com.lureclub.points.entity.announcement.vo.response.AnnouncementListVo;
import com.lureclub.points.entity.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 用户公告API接口
 *
 * @author system
 * @date 2025-06-19
 */
@Tag(name = "用户公告接口", description = "用户查看公告相关接口")
@RequestMapping("/api/user/announcement")
public interface UserAnnouncementApi {

    /**
     * 获取所有公告内容
     *
     * @return 公告列表
     */
    @Operation(summary = "获取所有公告", description = "获取所有公告内容，按发布日期倒序排列")
    @GetMapping("")
    ApiResponse<List<AnnouncementListVo>> getAllAnnouncements();

    /**
     * 根据日期搜索公告
     *
     * @param searchVo 搜索条件
     * @return 公告列表
     */
    @Operation(summary = "搜索公告", description = "根据日期范围搜索公告内容")
    @PostMapping("/search")
    ApiResponse<List<AnnouncementListVo>> searchAnnouncements(@Valid @RequestBody AnnouncementSearchVo searchVo);

    /**
     * 获取公告详情
     *
     * @param id 公告ID
     * @return 公告详情
     */
    @Operation(summary = "获取公告详情", description = "根据公告ID获取公告的详细内容")
    @GetMapping("/{id}")
    ApiResponse<AnnouncementVo> getAnnouncementDetail(@Parameter(description = "公告ID") @PathVariable Long id);

}