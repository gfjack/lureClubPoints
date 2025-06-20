package com.lureclub.points.config;

import com.lureclub.points.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JWT认证过滤器 - 最终版
 * 适配访问路径：http://localhost:8080
 *
 * @author system
 * @date 2025-06-20
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        logger.debug("JWT过滤器处理请求: {} {}", method, requestURI);

        try {
            String token = getTokenFromRequest(request);

            if (StringUtils.hasText(token)) {
                logger.debug("找到Token，开始验证...");

                if (jwtUtil.validateToken(token)) {
                    Long userId = jwtUtil.getUserIdFromToken(token);
                    logger.debug("Token验证成功，用户ID: {}", userId);

                    if (isAdminUser(userId)) {
                        // 管理员：负数ID转为正数，设置ROLE_ADMIN权限
                        Long realAdminId = Math.abs(userId);
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        realAdminId,
                                        null,
                                        Collections.singletonList(new SimpleGrantedAuthority(ROLE_ADMIN))
                                );
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        logger.debug("✅ 管理员认证成功，ID: {}, 权限: {}", realAdminId, ROLE_ADMIN);

                    } else {
                        // 普通用户：正数ID，设置ROLE_USER权限
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userId,
                                        null,
                                        Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER))
                                );
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        logger.debug("✅ 用户认证成功，ID: {}, 权限: {}", userId, ROLE_USER);
                    }
                } else {
                    logger.warn("Token验证失败");
                    SecurityContextHolder.clearContext();
                }
            } else {
                logger.debug("请求中未找到Token");
            }
        } catch (Exception e) {
            logger.error("JWT认证异常: {}", e.getMessage(), e);
            SecurityContextHolder.clearContext();
        }

        // 继续过滤链
        filterChain.doFilter(request, response);
    }

    /**
     * 指定哪些路径跳过JWT过滤器
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        // 跳过认证相关接口
        if (isAuthPath(path)) {
            logger.debug("⏭️ 跳过认证路径: {}", path);
            return true;
        }

        // 跳过Swagger相关接口
        if (isSwaggerPath(path)) {
            logger.debug("⏭️ 跳过Swagger路径: {}", path);
            return true;
        }

        // 跳过静态资源和系统接口
        if (isPublicPath(path)) {
            logger.debug("⏭️ 跳过公共路径: {}", path);
            return true;
        }

        logger.debug("🔍 需要JWT验证的路径: {}", path);
        return false;
    }

    /**
     * 认证相关路径判断
     */
    private boolean isAuthPath(String path) {
        return path.equals("/lureclub/api/user/auth/login") ||
                path.equals("/lureclub/api/user/auth/register") ||
                path.equals("/lureclub/api/admin/auth/login") ||
                path.equals("/lureclub/api/admin/auth/create");
    }

    /**
     * Swagger相关路径判断
     */
    private boolean isSwaggerPath(String path) {
        return path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/webjars") ||
                path.startsWith("/api-docs") ||
                path.equals("/swagger-ui.html") ||
                path.equals("/v3/api-docs.yaml");
    }

    /**
     * 公共路径判断
     */
    private boolean isPublicPath(String path) {
        return path.startsWith("/uploads/") ||
                path.startsWith("/static/") ||
                path.equals("/favicon.ico") ||
                path.equals("/error") ||
                path.equals("/health") ||
                path.startsWith("/actuator/");
    }

    /**
     * 判断是否为管理员用户（负数ID）
     */
    private boolean isAdminUser(Long userId) {
        return userId != null && userId < 0;
    }

    /**
     * 从请求中提取Token
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        // 1. 从Authorization头获取 Bearer Token
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        // 2. 从请求参数获取（兼容性支持）
        String paramToken = request.getParameter("token");
        if (StringUtils.hasText(paramToken)) {
            return paramToken;
        }

        return null;
    }

    /**
     * 获取当前认证用户ID
     */
    public static Long getCurrentUserId() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof Long) {
                return (Long) principal;
            }
        } catch (Exception e) {
            logger.debug("获取当前用户ID失败: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 获取当前认证用户角色
     */
    public static String getCurrentUserRole() {
        try {
            return SecurityContextHolder.getContext().getAuthentication()
                    .getAuthorities().iterator().next().getAuthority();
        } catch (Exception e) {
            logger.debug("获取当前用户角色失败: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 判断当前用户是否为管理员
     */
    public static boolean isCurrentUserAdmin() {
        return ROLE_ADMIN.equals(getCurrentUserRole());
    }

    /**
     * 判断当前用户是否为普通用户
     */
    public static boolean isCurrentUserRegular() {
        return ROLE_USER.equals(getCurrentUserRole());
    }
}

