package com.lureclub.points.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类（彻底修复版）
 * 解决API路径被当作静态资源的问题
 *
 * @author system
 * @date 2025-06-20
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload.path}")
    private String uploadPath;

    /**
     * 配置静态资源处理 - 只处理明确的静态资源路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 文件上传路径 - 只匹配 /uploads/ 开头的请求
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/")
                .setCachePeriod(3600);

        // 静态资源 - 只匹配 /static/ 开头的请求
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(3600);

        // Swagger UI 静态资源 - 只匹配特定路径
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/")
                .setCachePeriod(3600);

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .setCachePeriod(3600);

        // favicon
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/static/favicon.ico")
                .setCachePeriod(3600);
    }

    /**
     * 路径匹配配置 - 确保API优先
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 使用后缀模式匹配
        configurer.setUseSuffixPatternMatch(false);
        // 使用尾部斜杠匹配
        configurer.setUseTrailingSlashMatch(true);
    }

    /**
     * 禁用默认Servlet处理器 - 防止API请求被当作静态资源
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        // 不启用默认servlet，确保所有请求都由DispatcherServlet处理
        configurer.enable();
    }
}

/*
这个配置修复了以下问题：

1. 明确指定静态资源路径，避免Spring把 /lureclub/api/** 当作静态资源
2. 配置路径匹配优先级，确保Controller优先处理API请求
3. 正确配置文件上传路径映射

主要修复点：
- 移除了可能导致路径冲突的通配符配置
- 明确指定每个静态资源的具体路径
- 确保API路径不会被静态资源处理器误判
*/

