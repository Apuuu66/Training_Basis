package com.juc.thread.lock;

public class Ticket implements Runnable {
    //当前拥有的票数
    private int num = 100;

    public void run() {
        while (true) {

            if (num > 0) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
                //输出卖票信息
                System.out.println(Thread.currentThread().getName() + ".....sale...." + num--);
            } else
                break;
        }
    }
}
