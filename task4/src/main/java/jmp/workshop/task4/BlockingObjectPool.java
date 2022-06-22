package jmp.workshop.task4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 22/06/2022
 */
public class BlockingObjectPool {

    private final Random random = new Random();
    private final Queue<Object> pool;
    private final int size;

    private static final Object FULL = new Object();
    private static final Object EMPTY = new Object();

    /**
     * Creates filled pool of passed size
     *
     * @param size of pool
     */
    public BlockingObjectPool(int size) {
        this.size = size;
        this.pool = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            pool.add(new Object());
        }
    }

    /**
     * Gets object from pool or blocks if pool is empty
     *
     * @return object from pool
     */
    public Object get() throws InterruptedException {
        Object object;
        while (isEmpty()) {
            waitOnEmpty();
        }
        synchronized (this) {
            object = pool.poll();
            notifyAllForFull();
            System.out.println("Object is taken from the pool.");
            System.out.println("Pool size is " + pool.size());
        }
        Thread.sleep(random.nextInt(1000));
        return object;
    }

    /**
     * Puts object to pool or blocks if pool is full
     *
     * @param object to be taken back to pool
     */
    public void take(Object object) throws InterruptedException {
        if (isFull()) {
            waitOnFull();
        }
        synchronized (this) {
            pool.add(object);
            notifyAllForEmpty();
            System.out.println("New object is added to the pool.");
            System.out.println("Pool size is " + pool.size());
        }
        Thread.sleep(random.nextInt(1000));
    }

    private synchronized boolean isEmpty() {
        return pool.isEmpty();
    }

    private synchronized boolean isFull() {
        return pool.size() == size;
    }

    private void waitOnFull() throws InterruptedException {
        synchronized (FULL) {
            FULL.wait();
        }
    }

    private void waitOnEmpty() throws InterruptedException {
        synchronized (EMPTY) {
            EMPTY.wait();
        }
    }

    private void notifyAllForFull() {
        synchronized (FULL) {
            FULL.notifyAll();
        }
    }

    private void notifyAllForEmpty() {
        synchronized (EMPTY) {
            EMPTY.notifyAll();
        }
    }

}
