package com.lureclub.points.service.impl;

import com.lureclub.points.entity.announcement.Announcement;
import com.lureclub.points.entity.announcement.vo.request.AnnouncementCreateVo;
import com.lureclub.points.entity.announcement.vo.request.AnnouncementUpdateVo;
import com.lureclub.points.entity.announcement.vo.request.AnnouncementSearchVo;
import com.lureclub.points.entity.announcement.vo.response.AnnouncementVo;
import com.lureclub.points.entity.announcement.vo.response.AnnouncementListVo;
import com.lureclub.points.exception.BusinessException;
import com.lureclub.points.repository.AnnouncementRepository;
import com.lureclub.points.service.AnnouncementService;
import com.lureclub.points.entity.announcement.AnnouncementConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 公告服务实现类
 *
 * @author system
 * @date 2025-06-19
 */
@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private AnnouncementConverter announcementConverter;

    /**
     * 获取所有公告实现（用户端）
     */
    @Override
    public List<AnnouncementListVo> getAllAnnouncements() {
        List<Announcement> announcements = announcementRepository.findAllActiveOrderByTopAndDate();

        return announcements.stream()
                .map(announcementConverter::toAnnouncementListVo)
                .collect(Collectors.toList());
    }

    /**
     * 搜索公告实现（用户端）
     */
    @Override
    public List<AnnouncementListVo> searchAnnouncements(AnnouncementSearchVo searchVo) {
        List<Announcement> announcements;

        if (searchVo.getStartDate() != null || searchVo.getEndDate() != null) {
            // 按日期范围搜索
            announcements = announcementRepository.findByDateRange(
                    searchVo.getStartDate(),
                    searchVo.getEndDate()
            );
        } else if (StringUtils.hasText(searchVo.getKeyword())) {
            // 按关键词搜索
            announcements = announcementRepository.findByKeyword(searchVo.getKeyword());
        } else if (Boolean.TRUE.equals(searchVo.getOnlyTop())) {
            // 只显示置顶公告
            announcements = announcementRepository.findTopAnnouncements();
        } else {
            // 获取所有公告
            announcements = announcementRepository.findAllActiveOrderByTopAndDate();
        }

        return announcements.stream()
                .map(announcementConverter::toAnnouncementListVo)
                .collect(Collectors.toList());
    }

    /**
     * 获取公告详情实现
     */
    @Override
    public AnnouncementVo getAnnouncementDetail(Long id) {
        Announcement announcement = announcementRepository.findByIdAndIsDeleted(id, 0);

        if (announcement == null) {
            throw new BusinessException("公告不存在");
        }

        return announcementConverter.toAnnouncementVo(announcement);
    }

    /**
     * 创建公告实现（管理员）
     */
    @Override
    @Transactional
    public AnnouncementVo createAnnouncement(AnnouncementCreateVo createVo) {
        Announcement announcement = new Announcement();
        announcement.setTitle(createVo.getTitle());
        announcement.setContent(createVo.getContent());

        if (createVo.getPublishDate() != null) {
            announcement.setPublishDate(createVo.getPublishDate());
        }

        if (createVo.getIsTop() != null) {
            announcement.setIsTop(createVo.getIsTop());
        }

        announcement = announcementRepository.save(announcement);

        return announcementConverter.toAnnouncementVo(announcement);
    }

    /**
     * 更新公告实现（管理员）
     */
    @Override
    @Transactional
    public AnnouncementVo updateAnnouncement(Long id, AnnouncementUpdateVo updateVo) {
        Announcement announcement = announcementRepository.findByIdAndIsDeleted(id, 0);

        if (announcement == null) {
            throw new BusinessException("公告不存在");
        }

        if (StringUtils.hasText(updateVo.getTitle())) {
            announcement.setTitle(updateVo.getTitle());
        }

        if (StringUtils.hasText(updateVo.getContent())) {
            announcement.setContent(updateVo.getContent());
        }

        if (updateVo.getPublishDate() != null) {
            announcement.setPublishDate(updateVo.getPublishDate());
        }

        if (updateVo.getIsTop() != null) {
            announcement.setIsTop(updateVo.getIsTop());
        }

        announcement = announcementRepository.save(announcement);

        return announcementConverter.toAnnouncementVo(announcement);
    }

    /**
     * 删除公告实现（管理员）
     */
    @Override
    @Transactional
    public void deleteAnnouncement(Long id) {
        Announcement announcement = announcementRepository.findByIdAndIsDeleted(id, 0);

        if (announcement == null) {
            throw new BusinessException("公告不存在");
        }

        announcement.setIsDeleted(1);
        announcementRepository.save(announcement);
    }

    /**
     * 获取所有公告实现（管理员端）
     */
    @Override
    public List<AnnouncementVo> getAllAnnouncementsForAdmin() {
        List<Announcement> announcements = announcementRepository.findAllActive();

        return announcements.stream()
                .map(announcementConverter::toAnnouncementVo)
                .collect(Collectors.toList());
    }

}