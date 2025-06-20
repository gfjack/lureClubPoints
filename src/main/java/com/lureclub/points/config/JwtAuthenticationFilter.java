//package com.lureclub.points.config;
//
//import com.lureclub.points.util.JwtUtil;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Collections;
//
///**
// * JWT认证过滤器 - 最终正确版
// *
// * 重要：当使用context-path时，过滤器内部看到的路径是去掉context-path后的路径
// *
// * @author system
// * @date 2025-06-20
// */
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    private static final String ROLE_USER = "ROLE_USER";
//    private static final String ROLE_ADMIN = "ROLE_ADMIN";
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        String requestURI = request.getRequestURI();
//        String method = request.getMethod();
//
//        logger.debug("🔍 JWT过滤器处理请求: {} {}", method, requestURI);
//
//        try {
//            String token = getTokenFromRequest(request);
//
//            if (StringUtils.hasText(token)) {
//                logger.debug("📝 找到Token，开始验证...");
//
//                if (jwtUtil.validateToken(token)) {
//                    Long userId = jwtUtil.getUserIdFromToken(token);
//                    logger.debug("✅ Token验证成功，用户ID: {}", userId);
//
//                    if (isAdminUser(userId)) {
//                        // 管理员：负数ID转为正数，设置ROLE_ADMIN权限
//                        Long realAdminId = Math.abs(userId);
//                        UsernamePasswordAuthenticationToken authentication =
//                                new UsernamePasswordAuthenticationToken(
//                                        realAdminId,
//                                        null,
//                                        Collections.singletonList(new SimpleGrantedAuthority(ROLE_ADMIN))
//                                );
//                        SecurityContextHolder.getContext().setAuthentication(authentication);
//                        logger.debug("👑 管理员认证成功，ID: {}, 权限: {}", realAdminId, ROLE_ADMIN);
//
//                    } else {
//                        // 普通用户：正数ID，设置ROLE_USER权限
//                        UsernamePasswordAuthenticationToken authentication =
//                                new UsernamePasswordAuthenticationToken(
//                                        userId,
//                                        null,
//                                        Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER))
//                                );
//                        SecurityContextHolder.getContext().setAuthentication(authentication);
//                        logger.debug("👤 用户认证成功，ID: {}, 权限: {}", userId, ROLE_USER);
//                    }
//                } else {
//                    logger.warn("❌ Token验证失败");
//                    SecurityContextHolder.clearContext();
//                }
//            } else {
//                logger.debug("🔓 请求中未找到Token");
//            }
//        } catch (Exception e) {
//            logger.error("💥 JWT认证异常: {}", e.getMessage(), e);
//            SecurityContextHolder.clearContext();
//        }
//
//        // 🔥 重要：无论如何都要继续过滤链
//        filterChain.doFilter(request, response);
//    }
//
//    /**
//     * 🔥 修复：直接跳过登录注册接口，不让JWT过滤器处理它们
//     */
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        String path = request.getRequestURI();
//
//        // 跳过认证相关接口 - 直接不处理
//        if (isAuthPath(path)) {
//            logger.info("⏭️ 跳过认证接口，不进行JWT处理: {}", path);
//            return true;
//        }
//
//        // 跳过静态资源
//        if (isStaticResource(path) || isSwaggerPath(path) || isSystemPath(path)) {
//            logger.debug("⏭️ 跳过静态资源: {}", path);
//            return true;
//        }
//
//        // 其他API请求需要JWT处理
//        logger.debug("🔍 JWT过滤器将处理: {}", path);
//        return false;
//    }
//
//    /**
//     * 判断是否是认证路径（这些路径完全跳过JWT处理）
//     */
//    private boolean isAuthPath(String path) {
//        // 🔥 支持多种路径格式，确保都能被跳过
//        return path.equals("/lureclub/api/user/auth/login") ||
//                path.equals("/lureclub/api/user/auth/register") ||
//                path.equals("/lureclub/api/admin/auth/login") ||
//                path.equals("/lureclub/api/admin/auth/create") ||
//                // 也支持不带context-path的路径（以防万一）
//                path.equals("/api/user/auth/login") ||
//                path.equals("/api/user/auth/register") ||
//                path.equals("/api/admin/auth/login") ||
//                path.equals("/api/admin/auth/create");
//    }
//
//    /**
//     * 静态资源判断
//     */
//    private boolean isStaticResource(String path) {
//        return path.startsWith("/uploads/") ||
//                path.startsWith("/static/") ||
//                path.equals("/favicon.ico");
//    }
//
//    /**
//     * Swagger相关路径判断
//     */
//    private boolean isSwaggerPath(String path) {
//        return path.startsWith("/swagger-ui") ||
//                path.startsWith("/v3/api-docs") ||
//                path.startsWith("/swagger-resources") ||
//                path.startsWith("/webjars") ||
//                path.startsWith("/api-docs") ||
//                path.equals("/swagger-ui.html") ||
//                path.equals("/v3/api-docs.yaml");
//    }
//
//    /**
//     * 系统路径判断
//     */
//    private boolean isSystemPath(String path) {
//        return path.equals("/error") ||
//                path.equals("/health") ||
//                path.startsWith("/actuator/");
//    }
//
//    /**
//     * 判断是否为管理员用户（负数ID）
//     */
//    private boolean isAdminUser(Long userId) {
//        return userId != null && userId < 0;
//    }
//
//    /**
//     * 从请求中提取Token
//     */
//    private String getTokenFromRequest(HttpServletRequest request) {
//        // 1. 从Authorization头获取 Bearer Token
//        String bearerToken = request.getHeader("Authorization");
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//
//        // 2. 从请求参数获取（兼容性支持）
//        String paramToken = request.getParameter("token");
//        if (StringUtils.hasText(paramToken)) {
//            return paramToken;
//        }
//
//        return null;
//    }
//
//    /**
//     * 获取当前认证用户ID
//     */
//    public static Long getCurrentUserId() {
//        try {
//            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            if (principal instanceof Long) {
//                return (Long) principal;
//            }
//        } catch (Exception e) {
//            logger.debug("获取当前用户ID失败: {}", e.getMessage());
//        }
//        return null;
//    }
//
//    /**
//     * 获取当前认证用户角色
//     */
//    public static String getCurrentUserRole() {
//        try {
//            return SecurityContextHolder.getContext().getAuthentication()
//                    .getAuthorities().iterator().next().getAuthority();
//        } catch (Exception e) {
//            logger.debug("获取当前用户角色失败: {}", e.getMessage());
//        }
//        return null;
//    }
//
//    /**
//     * 判断当前用户是否为管理员
//     */
//    public static boolean isCurrentUserAdmin() {
//        return ROLE_ADMIN.equals(getCurrentUserRole());
//    }
//
//    /**
//     * 判断当前用户是否为普通用户
//     */
//    public static boolean isCurrentUserRegular() {
//        return ROLE_USER.equals(getCurrentUserRole());
//    }
//}

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
 * JWT认证过滤器 - 最终解决方案
 *
 * 现在负责所有的权限控制逻辑
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

        logger.debug("🔍 JWT过滤器处理请求: {} {}", method, requestURI);

        // 🔥 关键：检查是否是认证接口
        if (isAuthPath(requestURI)) {
            logger.info("🔓 认证接口，允许访问: {}", requestURI);
            // 直接放行，不设置任何认证信息
            filterChain.doFilter(request, response);
            return;
        }

        // 🔥 对于其他API接口，检查JWT token和权限
        if (isApiPath(requestURI)) {
            String token = getTokenFromRequest(request);

            if (!StringUtils.hasText(token)) {
                logger.warn("❌ API接口缺少token: {}", requestURI);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\":\"Missing authorization token\"}");
                return;
            }

            if (!jwtUtil.validateToken(token)) {
                logger.warn("❌ 无效token: {}", requestURI);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\":\"Invalid token\"}");
                return;
            }

            Long userId = jwtUtil.getUserIdFromToken(token);
            logger.debug("✅ Token验证成功，用户ID: {}", userId);

            // 🔥 权限检查
            if (isAdminPath(requestURI)) {
                // 管理员接口，需要管理员权限
                if (!isAdminUser(userId)) {
                    logger.warn("❌ 非管理员访问管理员接口: {}", requestURI);
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("{\"error\":\"Admin access required\"}");
                    return;
                }
                // 设置管理员认证
                Long realAdminId = Math.abs(userId);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                realAdminId,
                                null,
                                Collections.singletonList(new SimpleGrantedAuthority(ROLE_ADMIN))
                        );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("👑 管理员认证成功，ID: {}", realAdminId);

            } else if (isUserPath(requestURI)) {
                // 用户接口，需要用户权限
                if (isAdminUser(userId)) {
                    logger.warn("❌ 管理员访问用户接口: {}", requestURI);
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("{\"error\":\"User access required\"}");
                    return;
                }
                // 设置用户认证
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userId,
                                null,
                                Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER))
                        );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("👤 用户认证成功，ID: {}", userId);
            }
        }

        // 继续过滤链
        filterChain.doFilter(request, response);
    }

    /**
     * 跳过非API请求
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        // 跳过静态资源和系统接口
        if (isStaticResource(path) || isSwaggerPath(path) || isSystemPath(path)) {
            logger.debug("⏭️ 跳过静态资源: {}", path);
            return true;
        }

        // 所有API请求都需要处理
        return false;
    }

    /**
     * 判断是否是认证路径
     */
    private boolean isAuthPath(String path) {
        return path.equals("/api/user/auth/login") ||
                path.equals("/api/user/auth/register") ||
                path.equals("/api/admin/auth/login") ||
                path.equals("/api/admin/auth/create");
    }

    /**
     * 判断是否是API路径
     */
    private boolean isApiPath(String path) {
        return path.startsWith("/api/");
    }

    /**
     * 判断是否是管理员路径
     */
    private boolean isAdminPath(String path) {
        return path.startsWith("/api/admin/") && !isAuthPath(path);
    }

    /**
     * 判断是否是用户路径
     */
    private boolean isUserPath(String path) {
        return path.startsWith("/api/user/") && !isAuthPath(path);
    }

    /**
     * 静态资源判断
     */
    private boolean isStaticResource(String path) {
        return path.startsWith("/uploads/") ||
                path.startsWith("/static/") ||
                path.equals("/favicon.ico");
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
     * 系统路径判断
     */
    private boolean isSystemPath(String path) {
        return path.equals("/error") ||
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