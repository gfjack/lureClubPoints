package com.lureclub.points.repository;

import com.lureclub.points.entity.announcement.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * 公告数据访问接口
 *
 * @author system
 * @date 2025-06-19
 */
@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    /**
     * 查找未删除的公告，按置顶和发布日期排序
     *
     * @return 公告列表
     */
    @Query("SELECT a FROM Announcement a WHERE a.isDeleted = 0 ORDER BY a.isTop DESC, a.publishDate DESC, a.createTime DESC")
    List<Announcement> findAllActiveOrderByTopAndDate();

    /**
     * 根据ID和删除状态查找公告
     *
     * @param id 公告ID
     * @param isDeleted 删除状态
     * @return 公告
     */
    Announcement findByIdAndIsDeleted(Long id, Integer isDeleted);

    /**
     * 根据日期范围查找公告
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 公告列表
     */
    @Query("SELECT a FROM Announcement a WHERE a.isDeleted = 0 " +
            "AND (:startDate IS NULL OR a.publishDate >= :startDate) " +
            "AND (:endDate IS NULL OR a.publishDate <= :endDate) " +
            "ORDER BY a.isTop DESC, a.publishDate DESC")
    List<Announcement> findByDateRange(@Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate);

    /**
     * 根据关键词搜索公告（标题或内容）
     *
     * @param keyword 关键词
     * @return 公告列表
     */
    @Query("SELECT a FROM Announcement a WHERE a.isDeleted = 0 " +
            "AND (a.title LIKE %:keyword% OR a.content LIKE %:keyword%) " +
            "ORDER BY a.isTop DESC, a.publishDate DESC")
    List<Announcement> findByKeyword(@Param("keyword") String keyword);

    /**
     * 查找所有置顶公告
     *
     * @return 置顶公告列表
     */
    @Query("SELECT a FROM Announcement a WHERE a.isDeleted = 0 AND a.isTop = true " +
            "ORDER BY a.publishDate DESC, a.createTime DESC")
    List<Announcement> findTopAnnouncements();

    /**
     * 查找所有未删除的公告（管理员用）
     *
     * @return 公告列表
     */
    @Query("SELECT a FROM Announcement a WHERE a.isDeleted = 0 ORDER BY a.createTime DESC")
    List<Announcement> findAllActive();

}