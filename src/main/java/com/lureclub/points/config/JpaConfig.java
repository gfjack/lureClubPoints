package com.lureclub.points.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

/**
 * JPA配置类
 *
 * @author system
 * @date 2025-06-19
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.lureclub.points.repository")
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaConfig {

    /**
     * 配置审计功能的当前用户提供者
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAware<String>() {
            @Override
            public Optional<String> getCurrentAuditor() {
                // 这里可以从Security上下文中获取当前用户
                // 目前返回系统作为审计者
                return Optional.of("system");
            }
        };
    }

}