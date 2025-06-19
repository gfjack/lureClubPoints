package com.lureclub.points.api.admin;

import com.lureclub.points.entity.admin.vo.request.AdminLoginVo;
import com.lureclub.points.entity.admin.vo.request.AdminCreateVo;
import com.lureclub.points.entity.admin.vo.response.AdminVo;
import com.lureclub.points.entity.admin.vo.response.LoginVo;
import com.lureclub.points.entity.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 管理员认证API接口
 *
 * @author system
 * @date 2025-06-19
 */
@Tag(name = "管理员认证接口", description = "管理员登录、管理相关接口")
@RequestMapping("/api/admin/auth")
public interface AdminAuthApi {

    /**
     * 管理员登录
     *
     * @param loginVo 登录信息
     * @return 登录结果
     */
    @Operation(summary = "管理员登录", description = "管理员使用用户名和密码登录")
    @PostMapping("/login")
    ApiResponse<LoginVo> login(@Valid @RequestBody AdminLoginVo loginVo);

    /**
     * 获取当前管理员信息
     *
     * @return 管理员信息
     */
    @Operation(summary = "获取当前管理员信息", description = "根据token获取当前登录管理员的信息")
    @GetMapping("/current")
    ApiResponse<AdminVo> getCurrentAdmin();

    /**
     * 添加管理员
     *
     * @param createVo 管理员信息
     * @return 创建结果
     */
    @Operation(summary = "添加管理员", description = "预留接口增加管理员")
    @PostMapping("/create")
    ApiResponse<AdminVo> createAdmin(@Valid @RequestBody AdminCreateVo createVo);

    /**
     * 管理员登出
     *
     * @return 登出结果
     */
    @Operation(summary = "管理员登出", description = "管理员登出系统")
    @PostMapping("/logout")
    ApiResponse<String> logout();

}