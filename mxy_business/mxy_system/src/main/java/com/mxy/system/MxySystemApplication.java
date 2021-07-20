package com.mxy.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author 孟小耀
 * @Date 2021/7/18 0018 2:28
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.mxy"})
public class MxySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MxySystemApplication.class, args);
    }
}
