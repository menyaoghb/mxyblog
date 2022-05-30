package com.mxy.test.thread;

/**
 * 继承java.lang.Thread类
 * Thread类代表线程类，它的最主要的两个方法是：
 * run()：包含线程运行时所执行的代码。
 * start()：用于启动线程。
 */
public class MachineThreadTest extends Thread {
    @Override
    public void run() {
        for (int a = 0; a < 50; a++) {
            System.out.println(a);
        }
    }

    public static void main(String args[]) {
        MachineThreadTest machine = new MachineThreadTest();
        machine.start();  //启动machine线程
    }
}

