package com.lureclub.points.service.impl;

import com.lureclub.points.entity.prize.Prize;
import com.lureclub.points.entity.prize.vo.request.PrizeCreateVo;
import com.lureclub.points.entity.prize.vo.request.PrizeUpdateVo;
import com.lureclub.points.entity.prize.vo.response.PrizeVo;
import com.lureclub.points.entity.prize.vo.response.PrizeListVo;
import com.lureclub.points.exception.BusinessException;
import com.lureclub.points.repository.PrizeRepository;
import com.lureclub.points.service.PrizeService;
import com.lureclub.points.util.FileUploadUtil;
import com.lureclub.points.entity.prize.PrizeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 奖品服务实现类
 *
 * @author system
 * @date 2025-06-19
 */
@Service
public class PrizeServiceImpl implements PrizeService {

    @Autowired
    private PrizeRepository prizeRepository;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Autowired
    private PrizeConverter prizeConverter;

    /**
     * 获取所有奖品实现（用户端）
     */
    @Override
    public List<PrizeListVo> getAllPrizes() {
        List<Prize> prizes = prizeRepository.findAllActiveOrderBySortOrder();

        return prizes.stream()
                .map(prizeConverter::toPrizeListVo)
                .collect(Collectors.toList());
    }

    /**
     * 获取奖品详情实现
     */
    @Override
    public PrizeVo getPrizeDetail(Long id) {
        Prize prize = prizeRepository.findByIdAndIsDeletedAndIsEnabled(id, 0, true);

        if (prize == null) {
            throw new BusinessException("奖品不存在或已下架");
        }

        return prizeConverter.toPrizeVo(prize);
    }

    /**
     * 创建奖品实现（管理员）
     */
    @Override
    @Transactional
    public PrizeVo createPrize(PrizeCreateVo createVo, MultipartFile imageFile) {
        Prize prize = new Prize();
        prize.setName(createVo.getName());
        prize.setDescription(createVo.getDescription());

        if (createVo.getSortOrder() != null) {
            prize.setSortOrder(createVo.getSortOrder());
        }

        // 处理图片上传
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = fileUploadUtil.uploadPrizeImage(imageFile);
            prize.setImageUrl(imageUrl);
        }

        prize = prizeRepository.save(prize);

        return prizeConverter.toPrizeVo(prize);
    }

    /**
     * 更新奖品实现（管理员）
     */
    @Override
    @Transactional
    public PrizeVo updatePrize(Long id, PrizeUpdateVo updateVo, MultipartFile imageFile) {
        Prize prize = prizeRepository.findByIdAndIsDeleted(id, 0);

        if (prize == null) {
            throw new BusinessException("奖品不存在");
        }

        if (StringUtils.hasText(updateVo.getName())) {
            prize.setName(updateVo.getName());
        }

        if (StringUtils.hasText(updateVo.getDescription())) {
            prize.setDescription(updateVo.getDescription());
        }

        if (updateVo.getSortOrder() != null) {
            prize.setSortOrder(updateVo.getSortOrder());
        }

        if (updateVo.getIsEnabled() != null) {
            prize.setIsEnabled(updateVo.getIsEnabled());
        }

        // 处理图片上传
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = fileUploadUtil.uploadPrizeImage(imageFile);
            prize.setImageUrl(imageUrl);
        }

        prize = prizeRepository.save(prize);

        return prizeConverter.toPrizeVo(prize);
    }

    /**
     * 删除奖品实现（管理员）
     */
    @Override
    @Transactional
    public void deletePrize(Long id) {
        Prize prize = prizeRepository.findByIdAndIsDeleted(id, 0);

        if (prize == null) {
            throw new BusinessException("奖品不存在");
        }

        prize.setIsDeleted(1);
        prizeRepository.save(prize);
    }

    /**
     * 获取所有奖品实现（管理员端）
     */
    @Override
    public List<PrizeVo> getAllPrizesForAdmin() {
        List<Prize> prizes = prizeRepository.findAllActive();

        return prizes.stream()
                .map(prizeConverter::toPrizeVo)
                .collect(Collectors.toList());
    }

}