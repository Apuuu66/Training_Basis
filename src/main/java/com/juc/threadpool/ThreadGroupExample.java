package com.juc.threadpool;

public class ThreadGroupExample {
    public static ThreadGroup workerThreads = new MyThreadGroup("Worker Threads");

    public static class MyThreadGroup extends ThreadGroup {
        public MyThreadGroup(String s) {
            super(s);
        }

        public void uncaughtException(Thread thread, Throwable throwable) {
            System.out.println("Thread " + thread.getName()
                    + " died, exception was: ");
            throwable.printStackTrace();
        }
    }

    public static class WorkerThread extends Thread {
        public WorkerThread(String s) {
            super(workerThreads, s);
        }

        public void run() {
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) {
        Thread t = new WorkerThread("Worker Thread");
        t.start();
    }
}
