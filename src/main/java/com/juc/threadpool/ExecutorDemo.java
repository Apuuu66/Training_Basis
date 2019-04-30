package com.juc.threadpool;

import com.lang.reflect.Person;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorDemo {
    public static void main(String[] args) {
        List<Person> list = Arrays.asList(new Person(18, "张三"), new Person(19, "李四"));
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 5,
                100L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
        executor.submit(() -> {
            list.forEach((p) -> {
                try {
                    System.out.println(p.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        for (int i = 0; i < 10; i++) {
            executor.submit(new MyTask(i + "", i + ""));
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("存活："+executor.getActiveCount());
        System.out.println(executor);
        executor.shutdown();
    }
}
