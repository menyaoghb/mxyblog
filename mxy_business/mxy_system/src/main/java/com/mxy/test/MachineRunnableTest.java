package com.mxy.test;

/**
 * 实现Runnable接口
 */
public class MachineRunnableTest implements Runnable {
    private int a = 0;

    @Override
    public void run() {
        for (a = 0; a < 50; a++) {
            System.out.println(Thread.currentThread().getName() + ":" + a);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String args[]) {
        MachineRunnableTest machine = new MachineRunnableTest();
        Thread t1 = new Thread(machine);
        Thread t2 = new Thread(machine);
        t1.start();
        t2.start();
    }
}


