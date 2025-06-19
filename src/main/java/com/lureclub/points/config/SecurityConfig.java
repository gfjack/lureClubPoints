package com.lureclub.points.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Spring Security配置类（Spring Security 6.1兼容版本）
 *
 * @author system
 * @date 2025-06-19
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Value("${cors.allowed-origins:http://localhost:3000,http://localhost:8080}")
    private String allowedOrigins;

    /**
     * 配置Security过滤链
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.deny())
                        .contentTypeOptions(contentTypeOptions -> {})  // 修改：移除多余的.and()
                        .httpStrictTransportSecurity(hstsConfig -> hstsConfig
                                .maxAgeInSeconds(31536000)
                                .includeSubDomains(true))
                        .referrerPolicy(referrerPolicy -> referrerPolicy
                                .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
                )

                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(getPublicPaths()).permitAll()
                        .requestMatchers(getStaticResourcePaths()).permitAll()
                        .requestMatchers(getSwaggerPaths()).permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 获取公开访问路径
     */
    private String[] getPublicPaths() {
        return new String[]{
                "/api/user/auth/login",
                "/api/user/auth/register",
                "/api/admin/auth/login",
                "/api/admin/auth/create",
                "/health",
                "/actuator/health"
        };
    }

    /**
     * 获取静态资源路径
     */
    private String[] getStaticResourcePaths() {
        String[] basePaths = {
                "/uploads/**",
                "/static/**",
                "/favicon.ico"
        };

        if (contextPath != null && !contextPath.isEmpty() && !"/".equals(contextPath)) {
            String cleanContextPath = contextPath.startsWith("/") ? contextPath.substring(1) : contextPath;
            String[] contextPaths = {
                    "/" + cleanContextPath + "/uploads/**",
                    "/" + cleanContextPath + "/static/**"
            };

            String[] allPaths = new String[basePaths.length + contextPaths.length];
            System.arraycopy(basePaths, 0, allPaths, 0, basePaths.length);
            System.arraycopy(contextPaths, 0, allPaths, basePaths.length, contextPaths.length);
            return allPaths;
        }

        return basePaths;
    }

    /**
     * 获取Swagger文档路径
     */
    private String[] getSwaggerPaths() {
        return new String[]{
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**",
                "/v3/api-docs.yaml",
                "/swagger-resources/**",
                "/webjars/**"
        };
    }

    /**
     * CORS配置（Spring Security 6.1兼容版本）
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 处理允许的源地址
        if (allowedOrigins != null && !allowedOrigins.trim().isEmpty()) {
            String[] originArray = allowedOrigins.split(",");
            for (String origin : originArray) {
                String trimmedOrigin = origin.trim();
                if (!trimmedOrigin.isEmpty()) {
                    configuration.addAllowedOrigin(trimmedOrigin);
                }
            }
        } else {
            // 默认允许的源地址
            configuration.setAllowedOrigins(Arrays.asList(
                    "http://localhost:3000",
                    "http://localhost:8080",
                    "http://127.0.0.1:3000",
                    "http://127.0.0.1:8080"
            ));
        }

        // 允许的HTTP方法
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"
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

        // 预检请求的有效期
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}