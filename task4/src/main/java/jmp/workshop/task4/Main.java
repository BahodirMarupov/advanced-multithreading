package jmp.workshop.task4;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 22/06/2022
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        BlockingObjectPool objectPool = new BlockingObjectPool(5);
        Thread producer = new Thread(() -> {
            while (true) {
                try {
                    objectPool.take(new Object());
                } catch (InterruptedException e) {
                    System.out.printf("Exception in %s : %s\n", Thread.currentThread().getName(), e.getMessage());
                }
            }
        }, "Thread-Producer");
        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    objectPool.get();
                } catch (InterruptedException e) {
                    System.out.printf("Exception in %s : %s\n", Thread.currentThread().getName(), e.getMessage());
                }
            }
        }, "Thread-Consumer");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }
}
