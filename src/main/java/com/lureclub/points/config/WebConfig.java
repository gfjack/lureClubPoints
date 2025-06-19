package com.lureclub.points.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 *
 * @author system
 * @date 2025-06-19
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload.path}")
    private String uploadPath;

    /**
     * 配置静态资源处理
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置文件上传路径的静态资源映射
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath);

        // 配置默认静态资源
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

}