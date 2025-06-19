package com.lureclub.points.api.user;

import com.lureclub.points.entity.user.vo.request.UserLoginVo;
import com.lureclub.points.entity.user.vo.request.UserRegisterVo;
import com.lureclub.points.entity.user.vo.response.LoginVo;
import com.lureclub.points.entity.user.vo.response.UserVo;
import com.lureclub.points.entity.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 用户认证API接口
 *
 * @author system
 * @date 2025-06-19
 */
@Tag(name = "用户认证接口", description = "用户登录、注册相关接口")
@RequestMapping("/api/user/auth")
public interface UserAuthApi {

    /**
     * 用户登录接口
     *
     * @param loginVo 登录信息
     * @return 登录结果包含token
     */
    @Operation(summary = "用户登录", description = "用户使用用户名/手机号和密码登录")
    @PostMapping("/login")
    ApiResponse<LoginVo> login(@Valid @RequestBody UserLoginVo loginVo);

    /**
     * 用户注册接口
     *
     * @param registerVo 注册信息
     * @return 注册结果
     */
    @Operation(summary = "用户注册", description = "新用户注册，需要用户名、密码、手机号")
    @PostMapping("/register")
    ApiResponse<UserVo> register(@Valid @RequestBody UserRegisterVo registerVo);

    /**
     * 获取当前用户信息
     *
     * @return 当前登录用户信息
     */
    @Operation(summary = "获取当前用户信息", description = "根据token获取当前登录用户的详细信息")
    @GetMapping("/current")
    ApiResponse<UserVo> getCurrentUser();

    /**
     * 用户登出接口
     *
     * @return 登出结果
     */
    @Operation(summary = "用户登出", description = "用户登出系统")
    @PostMapping("/logout")
    ApiResponse<String> logout();

}