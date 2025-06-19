package com.lureclub.points.service;

import com.lureclub.points.entity.admin.vo.request.AdminLoginVo;
import com.lureclub.points.entity.admin.vo.request.AdminCreateVo;
import com.lureclub.points.entity.admin.vo.response.AdminListVo;
import com.lureclub.points.entity.admin.vo.response.AdminVo;
import com.lureclub.points.entity.admin.vo.response.LoginVo;

import java.util.List;

/**
 * 管理员服务接口
 *
 * @author system
 * @date 2025-06-19
 */
public interface AdminService {

    /**
     * 管理员登录
     *
     * @param loginVo 登录信息
     * @return 登录结果
     */
    LoginVo login(AdminLoginVo loginVo);

    /**
     * 获取当前管理员信息
     *
     * @return 当前管理员信息
     */
    AdminVo getCurrentAdmin();

    /**
     * 创建管理员
     *
     * @param createVo 管理员信息
     * @return 管理员信息
     */
    AdminVo createAdmin(AdminCreateVo createVo);

    /**
     * 管理员登出
     */
    void logout();

    // 在AdminService接口中添加方法
    /**
     * 获取管理员列表（用于列表显示）
     *
     * @return 管理员列表
     */
    List<AdminListVo> getAdminList();

    /**
     * 根据用户名查找管理员
     *
     * @param username 用户名
     * @return 管理员信息
     */
    AdminVo findByUsername(String username);

    /**
     * 获取所有管理员
     *
     * @return 管理员列表
     */
    List<AdminVo> getAllAdmins();

}