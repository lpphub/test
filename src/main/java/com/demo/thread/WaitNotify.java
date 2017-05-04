package com.demo.thread;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class WaitNotify {
    private static boolean flag = true;
    private static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Wait(), "waitThread").start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(new Notify(), "notifyThread").start();
    }

    static class Wait implements Runnable {
        @Override
        public void run() {
            //加锁,拥有lock的Monitor
            synchronized (lock) {
                //当条件不满足时, 继续wait, 同时释放了lock的锁
                while (flag) {
                    System.out.println(Thread.currentThread() + " flag is true, wait at:" +
                            LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("release lock after.");
                }
            }
        }
    }

    static class Notify implements Runnable {
        @Override
        public void run() {
            //加锁,拥有lock的Monitor
            synchronized (lock) {
                //获取lock的锁,然后进行通知,通知时不会释放lock锁
                //直到当前释放了lock之后, waitThread才能从wait方法返回
                System.out.println(Thread.currentThread() + " hold lock, notify at:" +
                        LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                lock.notifyAll();
                flag = false;

                sleep(5000);
            }

            //sleep(2);

            //再次加锁
            synchronized (lock) {
                System.out.println(Thread.currentThread() + " hold lock again, notify at:" +
                        LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

                sleep(5000);
            }
        }
    }

    private static void sleep(int times) {
        try {
            Thread.sleep(times);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
