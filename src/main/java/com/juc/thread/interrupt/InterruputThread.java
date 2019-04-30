package com.juc.thread.interrupt;

import java.util.concurrent.TimeUnit;

public class InterruputThread {
    public static void main(String[] args) throws InterruptedException {
        Thread t1= new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("未被中断");
            }
        });
        t1.start();
        TimeUnit.MILLISECONDS.sleep(2);
        t1.interrupt();
    }
}
