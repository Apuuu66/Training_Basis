package com.juc.thread.lock;

public class Resource {
    private String name;
    private int count = 1;
    private boolean flag = false;

    public synchronized void set(String name) {
        while (flag) {
            try {
                this.wait();
            } catch (Exception e) {
            }
        }
        this.name = name + "--" + count++;
        System.out.println(Thread.currentThread().getName() + "...Producer..." + this.name);
        this.flag = true;

        this.notifyAll();
    }

    public synchronized void out() {
        while (!flag) {
            try {
                this.wait();
            } catch (Exception e) {
            }
        }
        System.out.println(Thread.currentThread().getName() + ".....Consumer....." + this.name);
        this.flag = false;
        this.notifyAll();
    }

    public static void main(String[] args) {
        Resource r = new Resource();
        new Thread(new Producer(r)).start();
        new Thread(new Producer(r)).start();
        new Thread(new Consumer(r)).start();
        new Thread(new Consumer(r)).start();
    }

    static class Producer implements Runnable {
        private Resource r;

        public Producer(Resource r) {
            this.r = r;
        }

        public void run() {
            while (true) {
                r.set("+Items+");
            }
        }
    }

    static class Consumer implements Runnable {
        private Resource r;

        public Consumer(Resource r) {
            this.r = r;
        }

        public void run() {
            while (true) {
                r.out();
            }
        }
    }
}
