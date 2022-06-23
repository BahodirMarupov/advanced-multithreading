package jmp.workshop.task4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 22/06/2022
 */
public class BlockingObjectPool {

    private final static Random random = new Random();
    private final static Lock lock = new ReentrantLock();
    private final static Condition emptyCondition = lock.newCondition();
    private final static Condition fullCondition = lock.newCondition();

    private final Queue<Object> pool;
    private final int size;

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
        try {
            lock.lock();
            while (pool.isEmpty()) {
                emptyCondition.await();
            }

            object = pool.poll();
            fullCondition.signalAll();
            System.out.println("Object is taken from the pool.");
            System.out.println("Pool size is " + pool.size());
        } finally {
            lock.unlock();
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
        try {
            lock.lock();
            if (pool.size() == size) {
                fullCondition.await();
            }
            pool.add(object);
            emptyCondition.signalAll();
            System.out.println("New object is added to the pool.");
            System.out.println("Pool size is " + pool.size());
        } finally {
            lock.unlock();
        }
        Thread.sleep(random.nextInt(1000));
    }
}
