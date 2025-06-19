package com.lureclub.points.service.impl;

import com.lureclub.points.entity.user.User;
import com.lureclub.points.entity.user.vo.request.UserCreateVo;
import com.lureclub.points.entity.user.vo.request.UserUpdateVo;
import com.lureclub.points.entity.user.vo.request.UserSearchVo;
import com.lureclub.points.entity.user.vo.response.UserVo;
import com.lureclub.points.exception.BusinessException;
import com.lureclub.points.repository.UserRepository;
import com.lureclub.points.service.AdminUserService;
import com.lureclub.points.service.PointsService;
import com.lureclub.points.util.PasswordUtil;
import com.lureclub.points.util.ValidationUtil;
import com.lureclub.points.entity.user.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 管理员用户管理服务实现类
 *
 * @author system
 * @date 2025-06-19
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private PointsService pointsService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private ValidationUtil validationUtil;

    /**
     * 获取所有用户实现
     */
    @Override
    public List<UserVo> getAllUsers() {
        List<User> users = userRepository.findAllActiveUsers();
        return users.stream()
                .map(userConverter::toUserVo)
                .collect(Collectors.toList());
    }

    /**
     * 搜索用户实现
     */
    @Override
    public List<UserVo> searchUsers(UserSearchVo searchVo) {
        List<User> users;

        if (StringUtils.hasText(searchVo.getUsername()) && StringUtils.hasText(searchVo.getPhone())) {
            // 同时按用户名和手机号搜索
            users = userRepository.findByUsernameContainingAndPhoneContaining(
                    searchVo.getUsername(), searchVo.getPhone());
        } else if (StringUtils.hasText(searchVo.getUsername())) {
            // 按用户名搜索
            users = userRepository.findByUsernameContaining(searchVo.getUsername());
        } else if (StringUtils.hasText(searchVo.getPhone())) {
            // 按手机号搜索
            users = userRepository.findByPhoneContaining(searchVo.getPhone());
        } else {
            // 无搜索条件，返回所有用户
            users = userRepository.findAllActiveUsers();
        }

        return users.stream()
                .map(userConverter::toUserVo)
                .collect(Collectors.toList());
    }

    /**
     * 创建用户实现
     */
    @Override
    @Transactional
    public UserVo createUser(UserCreateVo createVo) {
        // 验证用户名格式
        if (!validationUtil.isValidUsername(createVo.getUsername())) {
            throw new BusinessException("用户名格式不正确");
        }

        // 验证密码强度
        if (!validationUtil.isValidPassword(createVo.getPassword())) {
            throw new BusinessException("密码长度必须在6-20个字符之间");
        }

        // 验证手机号格式
        if (!validationUtil.isValidPhone(createVo.getPhone())) {
            throw new BusinessException("手机号格式不正确");
        }

        // 检查用户名是否已存在
        if (userRepository.existsByUsername(createVo.getUsername())) {
            throw new BusinessException("用户名已存在");
        }

        // 检查手机号是否已存在
        if (userRepository.existsByPhone(createVo.getPhone())) {
            throw new BusinessException("手机号已存在");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(createVo.getUsername());
        user.setPassword(passwordUtil.encode(createVo.getPassword()));
        user.setPhone(createVo.getPhone());

        // 保存用户
        user = userRepository.save(user);

        // 【修复】初始化用户积分
        pointsService.initUserPoints(user.getId());

        return userConverter.toUserVo(user);
    }

    /**
     * 更新用户信息实现
     */
    @Override
    @Transactional
    public UserVo updateUser(Long userId, UserUpdateVo updateVo) {
        User user = userRepository.findActiveUserById(userId).orElse(null);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 更新用户名
        if (StringUtils.hasText(updateVo.getUsername()) && !updateVo.getUsername().equals(user.getUsername())) {
            if (!validationUtil.isValidUsername(updateVo.getUsername())) {
                throw new BusinessException("用户名格式不正确");
            }
            if (userRepository.existsByUsername(updateVo.getUsername())) {
                throw new BusinessException("用户名已存在");
            }
            user.setUsername(updateVo.getUsername());
        }

        // 更新密码
        if (StringUtils.hasText(updateVo.getPassword())) {
            if (!validationUtil.isValidPassword(updateVo.getPassword())) {
                throw new BusinessException("密码长度必须在6-20个字符之间");
            }
            user.setPassword(passwordUtil.encode(updateVo.getPassword()));
        }

        // 更新手机号
        if (StringUtils.hasText(updateVo.getPhone()) && !updateVo.getPhone().equals(user.getPhone())) {
            if (!validationUtil.isValidPhone(updateVo.getPhone())) {
                throw new BusinessException("手机号格式不正确");
            }
            if (userRepository.existsByPhone(updateVo.getPhone())) {
                throw new BusinessException("手机号已存在");
            }
            user.setPhone(updateVo.getPhone());
        }

        user = userRepository.save(user);
        return userConverter.toUserVo(user);
    }

    /**
     * 删除用户实现
     */
    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findActiveUserById(userId).orElse(null);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 软删除
        user.setIsDeleted(1);
        userRepository.save(user);
    }

    /**
     * 根据ID获取用户实现
     */
    @Override
    public UserVo getUserById(Long userId) {
        User user = userRepository.findActiveUserById(userId).orElse(null);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return userConverter.toUserVo(user);
    }

}