package com.lureclub.points.entity.announcement;

import com.lureclub.points.entity.announcement.vo.response.AnnouncementVo;
import com.lureclub.points.entity.announcement.vo.response.AnnouncementListVo;
import org.springframework.stereotype.Component;

/**
 * 公告转换器
 *
 * @author system
 * @date 2025-06-19
 */
@Component
public class AnnouncementConverter {

    /**
     * 将Announcement实体转换为AnnouncementVo
     *
     * @param announcement 公告实体
     * @return 公告VO
     */
    public AnnouncementVo toAnnouncementVo(Announcement announcement) {
        if (announcement == null) {
            return null;
        }
        return new AnnouncementVo(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getPublishDate(),
                announcement.getIsTop(),
                announcement.getCreateTime()
        );
    }

    /**
     * 将Announcement实体转换为AnnouncementListVo
     *
     * @param announcement 公告实体
     * @return 公告列表VO
     */
    public AnnouncementListVo toAnnouncementListVo(Announcement announcement) {
        if (announcement == null) {
            return null;
        }
        return new AnnouncementListVo(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getPublishDate(),
                announcement.getIsTop()
        );
    }

}