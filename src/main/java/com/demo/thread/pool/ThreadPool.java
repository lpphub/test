package com.demo.thread.pool;

public interface ThreadPool<Job extends Runnable> {
    void execute(Job job);

    void shutdown();

    int getJobSize();

}
