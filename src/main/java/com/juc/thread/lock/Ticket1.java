package com.juc.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ticket1 implements Runnable {
    //创建锁对象
    private Lock ticketLock = new ReentrantLock();
    //当前拥有的票数
    private int num = 100;

    public void run() {
        while (true) {
            try {
                ticketLock.lock();//获取锁
                if (num > 0) {
                    Thread.sleep(10);
                    System.out.println(Thread.currentThread().getName() + ".....sale...." + num--);  //输出卖票信息
                } else {
                    break;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();//出现异常就中断
            } finally {
                ticketLock.unlock();//释放锁
            }
        }
    }
}
