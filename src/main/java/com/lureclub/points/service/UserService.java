package com.lureclub.points.service;

import com.lureclub.points.entity.user.vo.request.UserLoginVo;
import com.lureclub.points.entity.user.vo.request.UserRegisterVo;
import com.lureclub.points.entity.user.vo.response.LoginVo;
import com.lureclub.points.entity.user.vo.response.UserVo;

/**
 * 用户服务接口
 *
 * @author system
 * @date 2025-06-19
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param loginVo 登录信息
     * @return 登录结果
     */
    LoginVo login(UserLoginVo loginVo);

    /**
     * 用户注册
     *
     * @param registerVo 注册信息
     * @return 用户信息
     */
    UserVo register(UserRegisterVo registerVo);

    /**
     * 获取当前用户信息
     *
     * @return 当前用户信息
     */
    UserVo getCurrentUser();

    /**
     * 用户登出
     */
    void logout();

    /**
     * 根据用户名或手机号查找用户
     *
     * @param username 用户名或手机号
     * @return 用户信息
     */
    UserVo findByUsernameOrPhone(String username);

    /**
     * 根据用户ID查找用户
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserVo findById(Long userId);

}