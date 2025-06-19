package com.lureclub.points.service.impl;

import com.lureclub.points.entity.admin.Admin;
import com.lureclub.points.entity.admin.vo.request.AdminLoginVo;
import com.lureclub.points.entity.admin.vo.request.AdminCreateVo;
import com.lureclub.points.entity.admin.vo.response.AdminListVo;
import com.lureclub.points.entity.admin.vo.response.AdminVo;
import com.lureclub.points.entity.admin.vo.response.LoginVo;
import com.lureclub.points.exception.BusinessException;
import com.lureclub.points.repository.AdminRepository;
import com.lureclub.points.service.AdminService;
import com.lureclub.points.util.JwtUtil;
import com.lureclub.points.util.PasswordUtil;
import com.lureclub.points.entity.admin.AdminConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 管理员服务实现类
 *
 * @author system
 * @date 2025-06-19
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private AdminConverter adminConverter;

    /**
     * 管理员登录实现
     */
    @Override
    public LoginVo login(AdminLoginVo loginVo) {
        // 根据用户名查找管理员
        Admin admin = adminRepository.findByUsername(loginVo.getUsername());

        if (admin == null || admin.getIsDeleted() == 1) {
            throw new BusinessException("管理员不存在");
        }

        if (!admin.getIsEnabled()) {
            throw new BusinessException("管理员账号已被禁用");
        }

        // 验证密码
        if (!passwordUtil.matches(loginVo.getPassword(), admin.getPassword())) {
            throw new BusinessException("密码错误");
        }

        // 生成JWT token（管理员ID前加负号区分普通用户）
        String token = jwtUtil.generateToken(-admin.getId());

        // 构建管理员信息
        AdminVo adminVo = adminConverter.toAdminVo(admin);

        return new LoginVo(token, adminVo);
    }

    /**
     * 获取当前管理员信息实现
     */
    @Override
    public AdminVo getCurrentAdmin() {
        Long adminId = getCurrentAdminId();
        Admin admin = adminRepository.findById(adminId).orElse(null);

        if (admin == null || admin.getIsDeleted() == 1) {
            throw new BusinessException("管理员不存在");
        }

        return adminConverter.toAdminVo(admin);
    }

    /**
     * 创建管理员实现
     */
    @Override
    public AdminVo createAdmin(AdminCreateVo createVo) {
        // 检查用户名是否已存在
        if (adminRepository.existsByUsername(createVo.getUsername())) {
            throw new BusinessException("管理员用户名已存在");
        }

        // 创建新管理员
        Admin admin = new Admin();
        admin.setUsername(createVo.getUsername());
        admin.setPassword(passwordUtil.encode(createVo.getPassword()));
        admin.setRealName(createVo.getRealName());

        // 保存管理员
        admin = adminRepository.save(admin);

        return adminConverter.toAdminVo(admin);
    }

    /**
     * 管理员登出实现
     */
    @Override
    public void logout() {
        // 清除安全上下文
        SecurityContextHolder.clearContext();
    }

    /**
     * 根据用户名查找管理员实现
     */
    @Override
    public AdminVo findByUsername(String username) {
        Admin admin = adminRepository.findByUsername(username);
        if (admin == null || admin.getIsDeleted() == 1) {
            return null;
        }
        return adminConverter.toAdminVo(admin);
    }

    /**
     * 获取所有管理员实现
     */
    @Override
    public List<AdminVo> getAllAdmins() {
        List<Admin> admins = adminRepository.findAllActive();

        return admins.stream()
                .map(adminConverter::toAdminVo)
                .collect(Collectors.toList());
    }

    /**
     * 获取当前登录管理员ID
     */
    private Long getCurrentAdminId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Long) {
            Long id = (Long) principal;
            // 管理员ID是负数，转换为正数
            return id < 0 ? -id : id;
        }
        throw new BusinessException("管理员未登录");
    }

    /**
     * 在AdminServiceImpl中实现
     */
    @Override
    public List<AdminListVo> getAdminList() {
        List<Admin> admins = adminRepository.findAllActive();

        return admins.stream()
                .map(admin -> new AdminListVo(
                        admin.getId(),
                        admin.getUsername(),
                        admin.getRealName(),
                        admin.getIsEnabled(),
                        admin.getCreateTime(),
                        null // 最后登录时间，如果需要可以添加字段
                ))
                .collect(Collectors.toList());
    }

}