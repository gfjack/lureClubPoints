package com.lureclub.points.entity.prize;

import com.lureclub.points.entity.prize.vo.response.PrizeVo;
import com.lureclub.points.entity.prize.vo.response.PrizeListVo;
import org.springframework.stereotype.Component;

/**
 * 奖品转换器
 *
 * @author system
 * @date 2025-06-19
 */
@Component
public class PrizeConverter {

    /**
     * 将Prize实体转换为PrizeVo
     *
     * @param prize 奖品实体
     * @return 奖品VO
     */
    public PrizeVo toPrizeVo(Prize prize) {
        if (prize == null) {
            return null;
        }
        return new PrizeVo(
                prize.getId(),
                prize.getName(),
                prize.getDescription(),
                prize.getImageUrl(),
                prize.getSortOrder(),
                prize.getIsEnabled(),
                prize.getCreateTime()
        );
    }

    /**
     * 将Prize实体转换为PrizeListVo
     *
     * @param prize 奖品实体
     * @return 奖品列表VO
     */
    public PrizeListVo toPrizeListVo(Prize prize) {
        if (prize == null) {
            return null;
        }
        return new PrizeListVo(
                prize.getId(),
                prize.getName(),
                prize.getDescription(),
                prize.getImageUrl(),
                prize.getSortOrder()
        );
    }

}