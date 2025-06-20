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
 * JWT认证过滤器（完整修复版）
 *
 * @author system
 * @date 2025-06-19
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

        try {
            String token = getTokenFromRequest(request);

            if (StringUtils.hasText(token) && jwtUtil.validateToken(token)) {
                Long userId = jwtUtil.getUserIdFromToken(token);

                if (isAdminUser(userId)) {
                    Long realAdminId = Math.abs(userId);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    realAdminId,
                                    null,
                                    Collections.singletonList(new SimpleGrantedAuthority(ROLE_ADMIN))
                            );
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    logger.debug("管理员认证成功，ID: {}", realAdminId);
                } else {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userId,
                                    null,
                                    Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER))
                            );
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    logger.debug("用户认证成功，ID: {}", userId);
                }
            } else {
                if (StringUtils.hasText(token)) {
                    logger.debug("Token验证失败: {}", token.substring(0, Math.min(token.length(), 20)) + "...");
                }
            }
        } catch (Exception e) {
            logger.error("JWT认证过程中发生异常: {}", e.getMessage(), e);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 判断是否为管理员用户
     */
    private boolean isAdminUser(Long userId) {
        return userId != null && userId < 0;
    }

    /**
     * 从请求头中获取Token
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        String paramToken = request.getParameter("token");
        if (StringUtils.hasText(paramToken)) {
            logger.debug("从请求参数获取到token");
            return paramToken;
        }

        return null;
    }

    /**
     * 优化性能，跳过不需要验证的路径
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        // 新增Swagger相关路径检查
        if (isSwaggerPath(path)) {
            return true;
        }

        return isPublicPath(path) || isStaticResource(path);
    }

    /**
     * 判断是否为Swagger相关路径
     */
    private boolean isSwaggerPath(String path) {
        String[] swaggerPaths = {
                "/swagger-ui/",
                "/v3/api-docs/",
                "/api-docs/",
                "/swagger-resources/",
                "/webjars/"
        };

        for (String swaggerPath : swaggerPaths) {
            if (path.startsWith(swaggerPath)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断是否为公开路径
     */
    private boolean isPublicPath(String path) {
        String[] publicPaths = {
                "/api/user/auth/login",
                "/api/user/auth/register",
                "/api/admin/auth/login",
                "/api/admin/auth/create",
                "/error",
                "/health",
                "/actuator/health"
        };

        for (String publicPath : publicPaths) {
            if (path.equals(publicPath) || path.startsWith(publicPath + "/")) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断是否为静态资源
     */
    private boolean isStaticResource(String path) {
        String[] staticPaths = {
                "/uploads/",
                "/static/",
                "/swagger-ui/",
                "/v3/api-docs/",
                "/favicon.ico"
        };

        for (String staticPath : staticPaths) {
            if (path.startsWith(staticPath)) {
                return true;
            }
        }

        return false;
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