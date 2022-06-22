package jmp.workshop.task2;

import java.util.Collection;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 22/06/2022
 */
public class ThirdThread implements Runnable {

    private final Collection<Integer> collection;
    private final Object lock;

    public ThirdThread(Collection<Integer> collection, Object lock) {
        this.collection = collection;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                double result = Math.sqrt(collection.stream().mapToInt(e -> e * e).sum());
                System.out.println("Square root of sum of squares of all numbers : " + result);
            }
        }
    }
}
