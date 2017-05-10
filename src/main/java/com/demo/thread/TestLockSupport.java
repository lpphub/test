package com.demo.thread;

import java.util.Date;
import java.util.concurrent.locks.LockSupport;

public class TestLockSupport {

    public static class MyRunnable implements Runnable {

        private final Thread currentThread;

        public MyRunnable(Thread thread) {
            this.currentThread = thread;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                LockSupport.unpark(this.currentThread);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new MyRunnable(Thread.currentThread()));
        thread.start();
        System.out.println(new Date());
        //unpark可以先于park
        Thread.sleep(5000);
        LockSupport.park();
        System.out.println(new Date());
    }
}
