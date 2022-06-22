package jmp.workshop.task2;

import java.util.Collection;
import java.util.Random;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 22/06/2022
 */
public class FirstThread implements Runnable {

    private final Collection<Integer> collection;
    private final Object lock;

    private final Random random = new Random();

    public FirstThread(Collection<Integer> collection, Object lock) {
        this.collection = collection;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                int value = random.nextInt(1000);
                collection.add(value);
                System.out.println("New value is added : " + value);
            }
        }
    }
}
