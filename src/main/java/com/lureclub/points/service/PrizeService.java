package com.lureclub.points.service;

import com.lureclub.points.entity.prize.vo.request.PrizeCreateVo;
import com.lureclub.points.entity.prize.vo.request.PrizeUpdateVo;
import com.lureclub.points.entity.prize.vo.response.PrizeVo;
import com.lureclub.points.entity.prize.vo.response.PrizeListVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 奖品服务接口
 *
 * @author system
 * @date 2025-06-19
 */
public interface PrizeService {

    /**
     * 获取所有奖品（用户端）
     *
     * @return 奖品列表
     */
    List<PrizeListVo> getAllPrizes();

    /**
     * 获取奖品详情
     *
     * @param id 奖品ID
     * @return 奖品详情
     */
    PrizeVo getPrizeDetail(Long id);

    /**
     * 创建奖品（管理员）
     *
     * @param createVo 创建信息
     * @param imageFile 图片文件
     * @return 奖品信息
     */
    PrizeVo createPrize(PrizeCreateVo createVo, MultipartFile imageFile);

    /**
     * 更新奖品（管理员）
     *
     * @param id 奖品ID
     * @param updateVo 更新信息
     * @param imageFile 图片文件（可选）
     * @return 奖品信息
     */
    PrizeVo updatePrize(Long id, PrizeUpdateVo updateVo, MultipartFile imageFile);

    /**
     * 删除奖品（管理员）
     *
     * @param id 奖品ID
     */
    void deletePrize(Long id);

    /**
     * 获取所有奖品（管理员端）
     *
     * @return 奖品列表
     */
    List<PrizeVo> getAllPrizesForAdmin();

}