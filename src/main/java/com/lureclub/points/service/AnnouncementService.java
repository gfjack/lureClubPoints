package com.lureclub.points.service;

import com.lureclub.points.entity.announcement.vo.request.AnnouncementCreateVo;
import com.lureclub.points.entity.announcement.vo.request.AnnouncementUpdateVo;
import com.lureclub.points.entity.announcement.vo.request.AnnouncementSearchVo;
import com.lureclub.points.entity.announcement.vo.response.AnnouncementVo;
import com.lureclub.points.entity.announcement.vo.response.AnnouncementListVo;

import java.util.List;

/**
 * 公告服务接口
 *
 * @author system
 * @date 2025-06-19
 */
public interface AnnouncementService {

    /**
     * 获取所有公告（用户端）
     *
     * @return 公告列表
     */
    List<AnnouncementListVo> getAllAnnouncements();

    /**
     * 搜索公告（用户端）
     *
     * @param searchVo 搜索条件
     * @return 公告列表
     */
    List<AnnouncementListVo> searchAnnouncements(AnnouncementSearchVo searchVo);

    /**
     * 获取公告详情
     *
     * @param id 公告ID
     * @return 公告详情
     */
    AnnouncementVo getAnnouncementDetail(Long id);

    /**
     * 创建公告（管理员）
     *
     * @param createVo 创建信息
     * @return 公告信息
     */
    AnnouncementVo createAnnouncement(AnnouncementCreateVo createVo);

    /**
     * 更新公告（管理员）
     *
     * @param id 公告ID
     * @param updateVo 更新信息
     * @return 公告信息
     */
    AnnouncementVo updateAnnouncement(Long id, AnnouncementUpdateVo updateVo);

    /**
     * 删除公告（管理员）
     *
     * @param id 公告ID
     */
    void deleteAnnouncement(Long id);

    /**
     * 获取所有公告（管理员端）
     *
     * @return 公告列表
     */
    List<AnnouncementVo> getAllAnnouncementsForAdmin();

}