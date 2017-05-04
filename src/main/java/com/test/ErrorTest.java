package com.test;

/**
 * Created by linshaokang on 15/7/27.
 */
public class ErrorTest {

//    public static void test() {
//        System.out.println("测试");
////        new NullPointerException().getMessage();
//        throw new RuntimeException("异常");
//    }

    public static void main(String[] args) {
//        packTest();
//        System.out.println("aaaaa");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("测试");
                    throw new RuntimeException("测试异常");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        t.start();

    }

//    public static void packTest() {
//        try {
//            test();
//        } catch (Exception e) {
//            System.out.println("测试。。。。。" + e.getMessage());
//        }
//    }
}
