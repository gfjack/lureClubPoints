package com.lureclub.points.entity.admin;

import com.lureclub.points.entity.admin.vo.response.AdminVo;
import org.springframework.stereotype.Component;

/**
 * 管理员转换器
 *
 * @author system
 * @date 2025-06-19
 */
@Component
public class AdminConverter {

    /**
     * 将Admin实体转换为AdminVo
     *
     * @param admin 管理员实体
     * @return 管理员VO
     */
    public AdminVo toAdminVo(Admin admin) {
        if (admin == null) {
            return null;
        }
        return new AdminVo(
                admin.getId(),
                admin.getUsername(),
                admin.getRealName(),
                admin.getIsEnabled(),
                admin.getCreateTime()
        );
    }

}