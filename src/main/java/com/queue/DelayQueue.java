package com.queue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DelayQueue<T> {
    private int cur;
    private Slot[] slots = new Slot[60];
    private HashMap<String, Node> map;
    private int size;

    public DelayQueue(int size) {
        this.size = size;
        for (int i = 0; i < slots.length; i++) {
            slots[i] = new Slot(size);
        }
    }

    private class Slot<T> {
        private int cur;
        private List[] slotQueue;
        private LinkedList<T> list;

        public Slot(int size) {
            this.cur = 0;
            this.slotQueue = new List[size];
            init();
        }

        public void init() {
            for (int i = 0; i < this.slotQueue.length; i++) {
                this.slotQueue[i] = new LinkedList();
            }
        }

    }

    private class Node {
        int state;
    }

    public void add(T t) {

    }

    public void del(T t) {

    }

    public T get(T t) {
        return null;
    }

}
