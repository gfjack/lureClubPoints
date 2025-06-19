package com.lureclub.points.api.admin;

import com.lureclub.points.entity.user.vo.request.UserCreateVo;
import com.lureclub.points.entity.user.vo.request.UserUpdateVo;
import com.lureclub.points.entity.user.vo.request.UserSearchVo;
import com.lureclub.points.entity.user.vo.response.UserVo;
import com.lureclub.points.entity.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 管理员用户管理API接口
 *
 * @author system
 * @date 2025-06-19
 */
@Tag(name = "管理员用户管理接口", description = "管理员用户管理相关接口")
@RequestMapping("/api/admin/user")
public interface AdminUserApi {

    /**
     * 获取所有用户
     *
     * @return 用户列表
     */
    @Operation(summary = "获取所有用户", description = "获取所有用户列表")
    @GetMapping("/all")
    ApiResponse<List<UserVo>> getAllUsers();

    /**
     * 搜索用户
     *
     * @param searchVo 搜索条件
     * @return 用户列表
     */
    @Operation(summary = "搜索用户", description = "根据条件搜索用户")
    @PostMapping("/search")
    ApiResponse<List<UserVo>> searchUsers(@Valid @RequestBody UserSearchVo searchVo);

    /**
     * 创建用户
     *
     * @param createVo 用户信息
     * @return 创建结果
     */
    @Operation(summary = "创建用户", description = "管理员创建新用户")
    @PostMapping("")
    ApiResponse<UserVo> createUser(@Valid @RequestBody UserCreateVo createVo);

    /**
     * 更新用户信息
     *
     * @param userId 用户ID
     * @param updateVo 更新信息
     * @return 更新结果
     */
    @Operation(summary = "更新用户", description = "管理员更新用户信息")
    @PutMapping("/{userId}")
    ApiResponse<UserVo> updateUser(@Parameter(description = "用户ID") @PathVariable Long userId,
                                   @Valid @RequestBody UserUpdateVo updateVo);

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return 删除结果
     */
    @Operation(summary = "删除用户", description = "管理员删除用户")
    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@Parameter(description = "用户ID") @PathVariable Long userId);

}