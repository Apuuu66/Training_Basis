package com.juc.threadpool;

import java.util.concurrent.TimeUnit;

public class MyTask implements Runnable {
    String name;
    String info;

    public MyTask() {
        this("task1", "run,run,run");
    }

    public MyTask(String name, String info) {
        this.name = name;
        this.info = info;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " : " + this.name);
    }
}
