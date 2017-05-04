package com.test;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class Main {
    private static TransferQueue<String> queue = new LinkedTransferQueue<String>();

    public static void main(String[] args) throws Exception {

//        new Productor(1).start();
//
//        Thread.sleep(100);
//
//        System.out.println("over.size=" + queue.size());

        String a = String.join(",", "a", "b", "c");
        System.out.println(a);
    }

    static class Productor extends Thread {
        private int id;

        public Productor(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                String result = "id=" + this.id;
                System.out.println("begin to produce." + result);
                queue.transfer(result);
                System.out.println("success to produce." + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
