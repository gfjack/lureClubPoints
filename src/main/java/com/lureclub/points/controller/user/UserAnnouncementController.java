package com.lureclub.points.controller.user;

import com.lureclub.points.api.user.UserAnnouncementApi;
import com.lureclub.points.entity.announcement.vo.request.AnnouncementSearchVo;
import com.lureclub.points.entity.announcement.vo.response.AnnouncementVo;
import com.lureclub.points.entity.announcement.vo.response.AnnouncementListVo;
import com.lureclub.points.entity.common.ApiResponse;
import com.lureclub.points.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 用户公告控制器实现
 *
 * @author system
 * @date 2025-06-19
 */
@RestController
@RequestMapping("/api/user/announcement")
public class UserAnnouncementController implements UserAnnouncementApi {

    @Autowired
    private AnnouncementService announcementService;

    /**
     * 获取所有公告内容实现
     */
    @Override
    @GetMapping("")
    public ApiResponse<List<AnnouncementListVo>> getAllAnnouncements() {
        try {
            List<AnnouncementListVo> result = announcementService.getAllAnnouncements();
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("获取公告列表失败: " + e.getMessage());
        }
    }

    /**
     * 根据日期搜索公告实现
     */
    @Override
    @PostMapping("/search")
    public ApiResponse<List<AnnouncementListVo>> searchAnnouncements(@Valid AnnouncementSearchVo searchVo) {
        try {
            List<AnnouncementListVo> result = announcementService.searchAnnouncements(searchVo);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("搜索公告失败: " + e.getMessage());
        }
    }

    /**
     * 获取公告详情实现
     */
    @Override
    @GetMapping("/{id}")
    public ApiResponse<AnnouncementVo> getAnnouncementDetail(Long id) {
        try {
            AnnouncementVo result = announcementService.getAnnouncementDetail(id);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("获取公告详情失败: " + e.getMessage());
        }
    }

}