package com.demo.thread.pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

    private static final int MAX_WORKER_NUMBERS = 10;
    private static final int DEFAULT_WORKER_NUMBERS = 5;
    private static final int MIN_WORKER_NUMBERS = 1;

    private final LinkedList<Job> jobs = new LinkedList<>();
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());

    private AtomicLong threadNumber = new AtomicLong();

    public DefaultThreadPool() {
        this(DEFAULT_WORKER_NUMBERS);
    }

    public DefaultThreadPool(int num) {
        init(num);
    }

    private void init(int num) {
        num = num < MIN_WORKER_NUMBERS ? MIN_WORKER_NUMBERS : num > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS : num;
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            new Thread(worker, "worker-" + threadNumber.incrementAndGet()).start();
        }
    }

    class Worker implements Runnable {
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                Job job = null;
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    job = jobs.removeFirst();
                }

                if (null != job) {
                    try {
                        job.run();
                    } catch (Exception ignore) {
                        ignore.printStackTrace();
                    }
                }
            }
        }

        public void shutdown() {
            running = false;
        }
    }


    @Override
    public void execute(Job job) {
        if (null != job) {
            synchronized (jobs) {
                jobs.add(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        workers.forEach(Worker::shutdown);
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }
}
