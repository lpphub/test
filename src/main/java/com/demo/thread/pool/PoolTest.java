package com.demo.thread.pool;

public class PoolTest {

    public static void main(String[] args) {
        ThreadPool pool = new DefaultThreadPool<>();

        pool.execute(() -> {
            System.out.println(Thread.currentThread().getName() + "测试一下:001");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        pool.execute(() -> {
            System.out.println(Thread.currentThread().getName() + "测试一下:002");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        pool.execute(() -> {
            System.out.println(Thread.currentThread().getName() + "测试一下:003");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        pool.execute(() -> {
            System.out.println(Thread.currentThread().getName() + "测试一下:004");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        pool.execute(() -> {
            System.out.println(Thread.currentThread().getName() + "测试一下:005");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        pool.execute(() -> {
            System.out.println(Thread.currentThread().getName() + "测试一下:006");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(pool.getJobSize());
//        pool.shutdown();
    }
}
