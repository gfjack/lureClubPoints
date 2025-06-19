package com.lureclub.points.service.impl;

import com.lureclub.points.entity.user.User;
import com.lureclub.points.entity.user.vo.request.UserLoginVo;
import com.lureclub.points.entity.user.vo.request.UserRegisterVo;
import com.lureclub.points.entity.user.vo.response.LoginVo;
import com.lureclub.points.entity.user.vo.response.UserVo;
import com.lureclub.points.exception.BusinessException;
import com.lureclub.points.repository.UserRepository;
import com.lureclub.points.service.UserService;
import com.lureclub.points.service.PointsService;
import com.lureclub.points.util.JwtUtil;
import com.lureclub.points.util.PasswordUtil;
import com.lureclub.points.util.ValidationUtil;
import com.lureclub.points.entity.user.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务实现类
 *
 * @author system
 * @date 2025-06-19
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private ValidationUtil validationUtil;

    @Autowired
    private PointsService pointsService;

    /**
     * 用户登录实现
     */
    @Override
    public LoginVo login(UserLoginVo loginVo) {
        // 根据用户名或手机号查找用户
        User user = userRepository.findByUsernameOrPhone(loginVo.getUsername(), loginVo.getUsername());

        if (user == null || user.getIsDeleted() == 1) {
            throw new BusinessException("用户不存在");
        }

        // 验证密码
        if (!passwordUtil.matches(loginVo.getPassword(), user.getPassword())) {
            throw new BusinessException("密码错误");
        }

        // 生成JWT token
        String token = jwtUtil.generateToken(user.getId());

        // 构建用户信息
        UserVo userVo = userConverter.toUserVo(user);

        return new LoginVo(token, userVo);
    }

    /**
     * 用户注册实现
     */
    @Override
    @Transactional
    public UserVo register(UserRegisterVo registerVo) {
        // 验证确认密码
        if (!registerVo.getPassword().equals(registerVo.getConfirmPassword())) {
            throw new BusinessException("两次输入的密码不一致");
        }

        // 验证用户名格式
        if (!validationUtil.isValidUsername(registerVo.getUsername())) {
            throw new BusinessException("用户名格式不正确");
        }

        // 验证密码强度
        if (!validationUtil.isValidPassword(registerVo.getPassword())) {
            throw new BusinessException("密码长度必须在6-20个字符之间");
        }

        // 验证手机号格式
        if (!validationUtil.isValidPhone(registerVo.getPhone())) {
            throw new BusinessException("手机号格式不正确");
        }

        // 检查用户名是否已存在
        if (userRepository.existsByUsername(registerVo.getUsername())) {
            throw new BusinessException("用户名已存在");
        }

        // 检查手机号是否已存在
        if (userRepository.existsByPhone(registerVo.getPhone())) {
            throw new BusinessException("手机号已存在");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(registerVo.getUsername());
        user.setPassword(passwordUtil.encode(registerVo.getPassword()));
        user.setPhone(registerVo.getPhone());

        // 保存用户
        user = userRepository.save(user);

        // 【修复】初始化用户积分记录
        pointsService.initUserPoints(user.getId());

        return userConverter.toUserVo(user);
    }

    /**
     * 获取当前用户信息实现
     */
    @Override
    public UserVo getCurrentUser() {
        Long userId = getCurrentUserId();
        User user = userRepository.findById(userId).orElse(null);

        if (user == null || user.getIsDeleted() == 1) {
            throw new BusinessException("用户不存在");
        }

        return userConverter.toUserVo(user);
    }

    /**
     * 用户登出实现
     */
    @Override
    public void logout() {
        // 清除安全上下文
        SecurityContextHolder.clearContext();
    }

    /**
     * 根据用户名或手机号查找用户
     */
    @Override
    public UserVo findByUsernameOrPhone(String username) {
        User user = userRepository.findByUsernameOrPhone(username, username);
        if (user == null || user.getIsDeleted() == 1) {
            return null;
        }
        return userConverter.toUserVo(user);
    }

    /**
     * 根据用户ID查找用户
     */
    @Override
    public UserVo findById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || user.getIsDeleted() == 1) {
            return null;
        }
        return userConverter.toUserVo(user);
    }

    /**
     * 获取当前登录用户ID
     */
    private Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Long) {
            return (Long) principal;
        }
        throw new BusinessException("用户未登录");
    }

}