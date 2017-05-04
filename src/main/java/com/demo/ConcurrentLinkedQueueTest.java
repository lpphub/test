package com.demo;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentLinkedQueueTest {
    private static ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Consumer consumer = new Consumer(queue);
        Producer producer = new Producer(queue);

        pool.execute(producer);
        pool.execute(consumer);
    }

    static class Consumer implements Runnable {
        private ConcurrentLinkedQueue<String> queue;

        public Consumer(ConcurrentLinkedQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                if (!queue.isEmpty()) {
                    System.out.println(Thread.currentThread().getName() + "消费数据:" + queue.poll());
                }
            }
        }
    }

    static class Producer implements Runnable {
        private ConcurrentLinkedQueue<String> queue;

        public Producer(ConcurrentLinkedQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "放入数据:" + i);
                queue.offer(String.valueOf(i));
                try {
                    Thread.sleep(3000);
                    System.out.println("等待三秒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
