package com.mxy.test.insert.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author mengyao
 * corePoolSize 核心线程数
 * maximumPoolSize  最大线程数
 * keepAliveTime    最大线程数可以存活的时间，就是线程池中除了核心线程之外的其他的最长可以保留的时间，
 *                  因为在线程池中，除了核心线程即使在无任务的情况下也不能被清除，其余的都是有存活时间的，意思就是非核心线程可以保留的最长的空闲时间
 *
 *
 */
@Configuration
public class ThreadPoolExecutorConfig {

    final int corePoolSize = 80;

    final int maximumPoolSize = 92;

    final int keepAliveTime = 30;

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }


    /**
     *  1、默认值
     *         * corePoolSize=1
     *         * queueCapacity=Integer.MAX_VALUE
     *         * maxPoolSize=Integer.MAX_VALUE
     *         * keepAliveTime=60s
     *         * allowCoreThreadTimeout=false
     *         * rejectedExecutionHandler=AbortPolicy()
     *
     *     2、如何来设置
     *         * 需要根据几个值来决定
     *             - tasks ：每秒的任务数，假设为500~1000
     *             - taskcost：每个任务花费时间，假设为0.1s
     *             - responsetime：系统允许容忍的最大响应时间，假设为1s
     *         * 做几个计算
     *             - corePoolSize = 每秒需要多少个线程处理？
     *                 * threadcount = tasks/(1/taskcost) =tasks*taskcout =  (500~1000)*0.1 = 50~100 个线程。corePoolSize设置应该大于50
     *                 * 根据8020原则，如果80%的每秒任务数小于800，那么corePoolSize设置为80即可
     *             - queueCapacity = (coreSizePool/taskcost)*responsetime
     *                 * 计算可得 queueCapacity = 80/0.1*1 = 80。意思是队列里的线程可以等待1s，超过了的需要新开线程来执行
     *                 * 切记不能设置为Integer.MAX_VALUE，这样队列会很大，线程数只会保持在corePoolSize大小，当任务陡增时，不能新开线程来执行，响应时间会随之陡增。
     *             - maxPoolSize = (max(tasks)- queueCapacity)/(1/taskcost)
     *                 * 计算可得 maxPoolSize = (1000-80)/10 = 92
     *                 * （最大任务数-队列容量）/每个线程每秒处理能力 = 最大线程数
     *             - rejectedExecutionHandler：根据具体情况来决定，任务不重要可丢弃，任务重要则要利用一些缓冲机制来处理
     *             - keepAliveTime和allowCoreThreadTimeout采用默认通常能满足
     *
     *     3、 以上都是理想值，实际情况下要根据机器性能来决定。如果在未达到最大线程数的情况机器cpu load已经满了，则需要通过升级硬件（呵呵）和优化代码，降低taskcost来处理。
     */
}
