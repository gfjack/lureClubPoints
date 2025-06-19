package com.lureclub.points.api.admin;

import com.lureclub.points.entity.announcement.vo.request.AnnouncementCreateVo;
import com.lureclub.points.entity.announcement.vo.request.AnnouncementUpdateVo;
import com.lureclub.points.entity.announcement.vo.response.AnnouncementVo;
import com.lureclub.points.entity.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 管理员公告管理API接口
 *
 * @author system
 * @date 2025-06-19
 */
@Tag(name = "管理员公告管理接口", description = "管理员公告管理相关接口")
@RequestMapping("/api/admin/announcement")
public interface AdminAnnouncementApi {

    /**
     * 获取所有公告（管理员）
     *
     * @return 公告列表
     */
    @Operation(summary = "获取所有公告", description = "管理员获取所有公告列表")
    @GetMapping("")
    ApiResponse<List<AnnouncementVo>> getAllAnnouncements();

    /**
     * 创建公告
     *
     * @param createVo 公告信息
     * @return 创建结果
     */
    @Operation(summary = "创建公告", description = "管理员创建新公告")
    @PostMapping("")
    ApiResponse<AnnouncementVo> createAnnouncement(@Valid @RequestBody AnnouncementCreateVo createVo);

    /**
     * 更新公告
     *
     * @param id 公告ID
     * @param updateVo 更新信息
     * @return 更新结果
     */
    @Operation(summary = "更新公告", description = "管理员更新公告信息")
    @PutMapping("/{id}")
    ApiResponse<AnnouncementVo> updateAnnouncement(@Parameter(description = "公告ID") @PathVariable Long id,
                                                   @Valid @RequestBody AnnouncementUpdateVo updateVo);

    /**
     * 删除公告
     *
     * @param id 公告ID
     * @return 删除结果
     */
    @Operation(summary = "删除公告", description = "管理员删除公告")
    @DeleteMapping("/{id}")
    ApiResponse<String> deleteAnnouncement(@Parameter(description = "公告ID") @PathVariable Long id);

}