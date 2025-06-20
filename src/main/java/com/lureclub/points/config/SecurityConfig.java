//package com.lureclub.points.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//
///**
// * Spring Security配置类 - 最终正确版
// *
// * 重要：当使用context-path时，Spring Security内部看到的路径是去掉context-path后的路径
// * 外部访问：http://localhost:8080/lureclub/api/admin/auth/login
// * Security内部路径：/api/admin/auth/login
// *
// * @author system
// * @date 2025-06-20
// */
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                // 禁用CSRF
//                .csrf(csrf -> csrf.disable())
//
//                // 配置CORS
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//
//                // 无状态会话
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//
//                // 配置请求授权规则
//                .authorizeHttpRequests(authz -> authz
//                        // 系统接口 - 完全公开
//                        .requestMatchers("/error", "/health", "/actuator/**").permitAll()
//
//                        // Swagger文档接口 - 完全公开
//                        .requestMatchers(
//                                "/swagger-ui/**",
//                                "/swagger-ui.html",
//                                "/v3/api-docs/**",
//                                "/v3/api-docs.yaml",
//                                "/swagger-resources/**",
//                                "/webjars/**",
//                                "/api-docs/**"
//                        ).permitAll()
//
//                        // 静态资源 - 完全公开
//                        .requestMatchers("/uploads/**", "/static/**", "/favicon.ico").permitAll()
//
//                        // 🔥 精确匹配认证相关接口
//                        .requestMatchers(
//                                "/lureclub/api/user/auth/login",
//                                "/lureclub/api/user/auth/register",
//                                "/lureclub/api/admin/auth/login",
//                                "/lureclub/api/admin/auth/create"
//                        ).permitAll()
//
//                        // 管理员接口 - 需要ROLE_ADMIN权限
//                        .requestMatchers("/lureclub/api/admin/**").hasAuthority("ROLE_ADMIN")
//
//                        // 用户接口 - 需要ROLE_USER权限
//                        .requestMatchers("/lureclub/api/user/**").hasAuthority("ROLE_USER")
//
//                        // 🔥 关键：匹配所有其他API路径，避免被当作静态资源
//                        .requestMatchers("/lureclub/api/**").authenticated()
//
//                        // 其他所有请求都需要认证
//                        .anyRequest().authenticated()
//                )
//
//                // 添加JWT过滤器
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    /**
//     * CORS配置
//     */
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        // 允许的源
//        configuration.setAllowedOrigins(Arrays.asList(
//                "http://localhost:3000",
//                "http://localhost:8080",
//                "http://127.0.0.1:3000",
//                "http://127.0.0.1:8080"
//        ));
//
//        // 允许的HTTP方法
//        configuration.setAllowedMethods(Arrays.asList(
//                "GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"
//        ));
//
//        // 允许的请求头
//        configuration.setAllowedHeaders(Arrays.asList(
//                "Authorization",
//                "Content-Type",
//                "X-Requested-With",
//                "Accept",
//                "Origin",
//                "Access-Control-Request-Method",
//                "Access-Control-Request-Headers"
//        ));
//
//        // 暴露的响应头
//        configuration.setExposedHeaders(Arrays.asList(
//                "Access-Control-Allow-Origin",
//                "Access-Control-Allow-Credentials",
//                "Authorization"
//        ));
//
//        // 允许携带凭证
//        configuration.setAllowCredentials(true);
//
//        // 预检请求有效期
//        configuration.setMaxAge(3600L);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}

package com.lureclub.points.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Spring Security配置类 - 最终解决方案
 *
 * 使用更简单直接的方法：对所有/api/路径进行统一处理
 *
 * @author system
 * @date 2025-06-20
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 禁用CSRF
                .csrf(csrf -> csrf.disable())

                // 配置CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 无状态会话
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 🔥 关键修复：使用web.ignoring()的替代方案
                .authorizeHttpRequests(authz -> authz
                        // 系统接口 - 完全公开
                        .requestMatchers("/error", "/health", "/actuator/**").permitAll()

                        // Swagger文档接口 - 完全公开
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/api-docs/**"
                        ).permitAll()

                        // 静态资源 - 完全公开
                        .requestMatchers("/uploads/**", "/static/**", "/favicon.ico").permitAll()

                        // 🔥 最简单的解决方案：所有API都先允许，然后在filter中控制
                        .requestMatchers("/api/**").permitAll()

                        // 其他所有请求都需要认证
                        .anyRequest().authenticated()
                )

                // 添加JWT过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * CORS配置
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 允许的源
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://localhost:8080",
                "http://127.0.0.1:3000",
                "http://127.0.0.1:8080"
        ));

        // 允许的HTTP方法
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"
        ));

        // 允许的请求头
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
        ));

        // 暴露的响应头
        configuration.setExposedHeaders(Arrays.asList(
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials",
                "Authorization"
        ));

        // 允许携带凭证
        configuration.setAllowCredentials(true);

        // 预检请求有效期
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}