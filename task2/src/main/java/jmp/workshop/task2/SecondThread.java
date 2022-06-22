package jmp.workshop.task2;

import java.util.Collection;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 22/06/2022
 */
public class SecondThread implements Runnable {

    private final Collection<Integer> collection;
    private final Object lock;

    public SecondThread(Collection<Integer> collection, Object lock) {
        this.collection = collection;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                int sum = collection.stream().mapToInt(value -> value).sum();
                System.out.println("Sum of collection elements : " + sum);
            }
        }
    }
}
