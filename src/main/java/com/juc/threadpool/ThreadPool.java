package com.juc.threadpool;

import java.util.concurrent.*;

public class ThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>());
        Future<?> submit = executor.submit(new MyTask());
        Object o = submit.get();
        System.out.println(o);
    }
}
