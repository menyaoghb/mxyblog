package com.mxy.test.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 孟耀
 * 线程池 测试类
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor();
        }
    }

    public static ThreadPoolExecutor threadPoolExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 6, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));        executor.allowCoreThreadTimeOut(true);
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }
}
