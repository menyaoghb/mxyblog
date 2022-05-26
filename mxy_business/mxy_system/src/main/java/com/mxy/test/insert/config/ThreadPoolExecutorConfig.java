package com.mxy.test.insert.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 孟耀
 * corePoolSize 核心线程数
 * maximumPoolSize  最大线程数
 * keepAliveTime    最大线程数可以存活的时间，就是线程池中除了核心线程之外的其他的最长可以保留的时间，
 *                  因为在线程池中，除了核心线程即使在无任务的情况下也不能被清除，其余的都是有存活时间的，意思就是非核心线程可以保留的最长的空闲时间
 *
 *
 */
@Configuration
public class ThreadPoolExecutorConfig {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 6, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }
}
