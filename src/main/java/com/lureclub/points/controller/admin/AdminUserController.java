package com.lureclub.points.controller.admin;

import com.lureclub.points.api.admin.AdminUserApi;
import com.lureclub.points.entity.user.vo.request.UserCreateVo;
import com.lureclub.points.entity.user.vo.request.UserUpdateVo;
import com.lureclub.points.entity.user.vo.request.UserSearchVo;
import com.lureclub.points.entity.user.vo.response.UserVo;
import com.lureclub.points.entity.common.ApiResponse;
import com.lureclub.points.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 管理员用户管理控制器实现
 *
 * @author system
 * @date 2025-06-19
 */
@RestController
@RequestMapping("/api/admin/user")
public class AdminUserController implements AdminUserApi {

    @Autowired
    private AdminUserService adminUserService;

    /**
     * 获取所有用户实现
     */
    @Override
    @GetMapping("/all")
    public ApiResponse<List<UserVo>> getAllUsers() {
        try {
            List<UserVo> result = adminUserService.getAllUsers();
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("获取用户列表失败: " + e.getMessage());
        }
    }

    /**
     * 搜索用户实现
     */
    @Override
    @PostMapping("/search")
    public ApiResponse<List<UserVo>> searchUsers(@Valid UserSearchVo searchVo) {
        try {
            List<UserVo> result = adminUserService.searchUsers(searchVo);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("搜索用户失败: " + e.getMessage());
        }
    }

    /**
     * 创建用户实现
     */
    @Override
    @PostMapping("")
    public ApiResponse<UserVo> createUser(@Valid UserCreateVo createVo) {
        try {
            UserVo result = adminUserService.createUser(createVo);
            return ApiResponse.success(result, "用户创建成功");
        } catch (Exception e) {
            return ApiResponse.error("创建用户失败: " + e.getMessage());
        }
    }

    /**
     * 更新用户信息实现
     */
    @Override
    @PutMapping("/{userId}")
    public ApiResponse<UserVo> updateUser(Long userId, @Valid UserUpdateVo updateVo) {
        try {
            UserVo result = adminUserService.updateUser(userId, updateVo);
            return ApiResponse.success(result, "用户更新成功");
        } catch (Exception e) {
            return ApiResponse.error("更新用户失败: " + e.getMessage());
        }
    }

    /**
     * 删除用户实现
     */
    @Override
    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteUser(Long userId) {
        try {
            adminUserService.deleteUser(userId);
            return ApiResponse.success("用户删除成功");
        } catch (Exception e) {
            return ApiResponse.error("删除用户失败: " + e.getMessage());
        }
    }

}