package com.lureclub.points.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import jakarta.servlet.MultipartConfigElement;

/**
 * 文件上传配置类
 *
 * @author system
 * @date 2025-06-19
 */
@Configuration
public class FileUploadConfig {

    /**
     * 配置文件上传解析器
     */
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    /**
     * 配置文件上传参数
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        // 设置单个文件最大大小
        factory.setMaxFileSize(DataSize.ofMegabytes(10));

        // 设置总上传数据最大大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(10));

        // 设置内存临界值
        factory.setFileSizeThreshold(DataSize.ofKilobytes(1024));

        return factory.createMultipartConfig();
    }

}