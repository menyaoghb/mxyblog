package com.mxy.system.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 孟小耀
 * @Date 2021/7/18 0018 2:36
 */
@Configuration
@MapperScan({"com.mxy.system.mapper", "com.mxy.system.demo.insert.mapper"})
public class MyBatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
