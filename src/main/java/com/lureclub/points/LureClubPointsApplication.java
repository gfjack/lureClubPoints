package com.lureclub.points;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 雷霆路亚俱乐部积分管理系统启动类
 *
 * @author system
 * @date 2025-06-19
 */
@SpringBootApplication
@EnableScheduling
public class LureClubPointsApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LureClubPointsApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(LureClubPointsApplication.class, args);
    }

}