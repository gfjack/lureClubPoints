package com.lureclub.points.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * 验证工具类
 *
 * @author system
 * @date 2025-06-19
 */
@Component
public class ValidationUtil {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_\\u4e00-\\u9fa5]{3,20}$");

    /**
     * 验证手机号格式
     *
     * @param phone 手机号
     * @return 是否有效
     */
    public boolean isValidPhone(String phone) {
        return StringUtils.hasText(phone) && PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * 验证用户名格式
     *
     * @param username 用户名
     * @return 是否有效
     */
    public boolean isValidUsername(String username) {
        return StringUtils.hasText(username) && USERNAME_PATTERN.matcher(username).matches();
    }

    /**
     * 验证密码强度
     *
     * @param password 密码
     * @return 是否有效
     */
    public boolean isValidPassword(String password) {
        return StringUtils.hasText(password) && password.length() >= 6 && password.length() <= 20;
    }

    /**
     * 手机号脱敏
     *
     * @param phone 手机号
     * @return 脱敏后的手机号
     */
    public String maskPhone(String phone) {
        if (!StringUtils.hasText(phone) || phone.length() != 11) {
            return phone;
        }
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }

    /**
     * 检查积分数量是否有效
     *
     * @param points 积分
     * @return 是否有效
     */
    public boolean isValidPoints(Integer points) {
        return points != null && points > 0 && points <= 10000;
    }

}