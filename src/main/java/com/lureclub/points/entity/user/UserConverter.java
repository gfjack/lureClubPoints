package com.lureclub.points.entity.user;

import com.lureclub.points.entity.user.vo.response.UserVo;
import org.springframework.stereotype.Component;

/**
 * 用户转换器
 *
 * @author system
 * @date 2025-06-19
 */
@Component
public class UserConverter {

    /**
     * 将User实体转换为UserVo
     *
     * @param user 用户实体
     * @return 用户VO
     */
    public UserVo toUserVo(User user) {
        if (user == null) {
            return null;
        }
        return new UserVo(
                user.getId(),
                user.getUsername(),
                user.getPhone(),
                user.getCreateTime()
        );
    }

    /**
     * 将User实体转换为UserVo（脱敏手机号）
     *
     * @param user 用户实体
     * @return 用户VO（手机号脱敏）
     */
    public UserVo toUserVoWithMaskedPhone(User user) {
        if (user == null) {
            return null;
        }

        String maskedPhone = maskPhone(user.getPhone());
        return new UserVo(
                user.getId(),
                user.getUsername(),
                maskedPhone,
                user.getCreateTime()
        );
    }

    /**
     * 手机号脱敏
     *
     * @param phone 手机号
     * @return 脱敏后的手机号
     */
    private String maskPhone(String phone) {
        if (phone == null || phone.length() != 11) {
            return phone;
        }
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }

}