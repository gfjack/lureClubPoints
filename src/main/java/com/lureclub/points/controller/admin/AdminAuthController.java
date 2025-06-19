package com.lureclub.points.controller.admin;

import com.lureclub.points.api.admin.AdminAuthApi;
import com.lureclub.points.entity.admin.vo.request.AdminLoginVo;
import com.lureclub.points.entity.admin.vo.request.AdminCreateVo;
import com.lureclub.points.entity.admin.vo.response.AdminVo;
import com.lureclub.points.entity.admin.vo.response.LoginVo;
import com.lureclub.points.entity.common.ApiResponse;
import com.lureclub.points.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * 管理员认证控制器实现
 *
 * @author system
 * @date 2025-06-19
 */
@RestController
public class AdminAuthController implements AdminAuthApi {

    @Autowired
    private AdminService adminService;

    /**
     * 管理员登录实现
     */
    @Override
    public ApiResponse<LoginVo> login(@Valid AdminLoginVo loginVo) {
        try {
            LoginVo result = adminService.login(loginVo);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("登录失败: " + e.getMessage());
        }
    }

    /**
     * 获取当前管理员信息实现
     */
    @Override
    public ApiResponse<AdminVo> getCurrentAdmin() {
        try {
            AdminVo result = adminService.getCurrentAdmin();
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("获取管理员信息失败: " + e.getMessage());
        }
    }

    /**
     * 添加管理员实现
     */
    @Override
    public ApiResponse<AdminVo> createAdmin(@Valid AdminCreateVo createVo) {
        try {
            AdminVo result = adminService.createAdmin(createVo);
            return ApiResponse.success(result, "管理员创建成功");
        } catch (Exception e) {
            return ApiResponse.error("创建管理员失败: " + e.getMessage());
        }
    }

    /**
     * 管理员登出实现
     */
    @Override
    public ApiResponse<String> logout() {
        try {
            adminService.logout();
            return ApiResponse.success("登出成功");
        } catch (Exception e) {
            return ApiResponse.error("登出失败: " + e.getMessage());
        }
    }

}