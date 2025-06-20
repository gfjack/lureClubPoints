package com.lureclub.points.controller.user;

import com.lureclub.points.api.user.UserAuthApi;
import com.lureclub.points.entity.user.vo.request.UserLoginVo;
import com.lureclub.points.entity.user.vo.request.UserRegisterVo;
import com.lureclub.points.entity.user.vo.response.LoginVo;
import com.lureclub.points.entity.user.vo.response.UserVo;
import com.lureclub.points.entity.common.ApiResponse;
import com.lureclub.points.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * 用户认证控制器实现
 *
 * @author system
 * @date 2025-06-19
 */
@RestController
@RequestMapping("/api/user/auth")
public class UserAuthController implements UserAuthApi {

    @Autowired
    private UserService userService;

    /**
     * 用户登录接口实现
     */
    @Override
    @PostMapping("/login")
    public ApiResponse<LoginVo> login(@Valid UserLoginVo loginVo) {
        try {
            LoginVo result = userService.login(loginVo);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("登录失败: " + e.getMessage());
        }
    }

    /**
     * 用户注册接口实现
     */
    @Override
    @PostMapping("/register")
    public ApiResponse<UserVo> register(@Valid UserRegisterVo registerVo) {
        try {
            UserVo result = userService.register(registerVo);
            return ApiResponse.success(result, "注册成功");
        } catch (Exception e) {
            return ApiResponse.error("注册失败: " + e.getMessage());
        }
    }

    /**
     * 获取当前用户信息实现
     */
    @Override
    @GetMapping("/current")
    public ApiResponse<UserVo> getCurrentUser() {
        try {
            UserVo result = userService.getCurrentUser();
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("获取用户信息失败: " + e.getMessage());
        }
    }

    /**
     * 用户登出接口实现
     */
    @Override
    @PostMapping("/logout")
    public ApiResponse<String> logout() {
        try {
            userService.logout();
            return ApiResponse.success("登出成功");
        } catch (Exception e) {
            return ApiResponse.error("登出失败: " + e.getMessage());
        }
    }

}