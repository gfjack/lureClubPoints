package com.lureclub.points.controller.admin;

import com.lureclub.points.api.admin.AdminAnnouncementApi;
import com.lureclub.points.entity.announcement.vo.request.AnnouncementCreateVo;
import com.lureclub.points.entity.announcement.vo.request.AnnouncementUpdateVo;
import com.lureclub.points.entity.announcement.vo.response.AnnouncementVo;
import com.lureclub.points.entity.common.ApiResponse;
import com.lureclub.points.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 管理员公告控制器实现
 *
 * @author system
 * @date 2025-06-19
 */
@RestController
@RequestMapping("/api/admin/announcement")
public class AdminAnnouncementController implements AdminAnnouncementApi {

    @Autowired
    private AnnouncementService announcementService;

    /**
     * 获取所有公告实现（管理员）
     */
    @Override
    @GetMapping("")
    public ApiResponse<List<AnnouncementVo>> getAllAnnouncements() {
        try {
            List<AnnouncementVo> result = announcementService.getAllAnnouncementsForAdmin();
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("获取公告列表失败: " + e.getMessage());
        }
    }

    /**
     * 创建公告实现
     */
    @Override
    @PostMapping("")
    public ApiResponse<AnnouncementVo> createAnnouncement(@Valid AnnouncementCreateVo createVo) {
        try {
            AnnouncementVo result = announcementService.createAnnouncement(createVo);
            return ApiResponse.success(result, "公告创建成功");
        } catch (Exception e) {
            return ApiResponse.error("创建公告失败: " + e.getMessage());
        }
    }

    /**
     * 更新公告实现
     */
    @Override
    @PutMapping("/{id}")
    public ApiResponse<AnnouncementVo> updateAnnouncement(Long id, @Valid AnnouncementUpdateVo updateVo) {
        try {
            AnnouncementVo result = announcementService.updateAnnouncement(id, updateVo);
            return ApiResponse.success(result, "公告更新成功");
        } catch (Exception e) {
            return ApiResponse.error("更新公告失败: " + e.getMessage());
        }
    }

    /**
     * 删除公告实现
     */
    @Override
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteAnnouncement(Long id) {
        try {
            announcementService.deleteAnnouncement(id);
            return ApiResponse.success("公告删除成功");
        } catch (Exception e) {
            return ApiResponse.error("删除公告失败: " + e.getMessage());
        }
    }

}