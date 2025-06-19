package com.lureclub.points.service;

import com.lureclub.points.entity.user.vo.request.UserCreateVo;
import com.lureclub.points.entity.user.vo.request.UserUpdateVo;
import com.lureclub.points.entity.user.vo.request.UserSearchVo;
import com.lureclub.points.entity.user.vo.response.UserVo;

import java.util.List;

/**
 * 管理员用户管理服务接口
 *
 * @author system
 * @date 2025-06-19
 */
public interface AdminUserService {

    /**
     * 获取所有用户
     *
     * @return 用户列表
     */
    List<UserVo> getAllUsers();

    /**
     * 搜索用户
     *
     * @param searchVo 搜索条件
     * @return 用户列表
     */
    List<UserVo> searchUsers(UserSearchVo searchVo);

    /**
     * 创建用户
     *
     * @param createVo 用户信息
     * @return 用户信息
     */
    UserVo createUser(UserCreateVo createVo);

    /**
     * 更新用户信息
     *
     * @param userId 用户ID
     * @param updateVo 更新信息
     * @return 用户信息
     */
    UserVo updateUser(Long userId, UserUpdateVo updateVo);

    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    void deleteUser(Long userId);

    /**
     * 根据ID获取用户
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserVo getUserById(Long userId);

}