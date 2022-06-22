package jmp.workshop.task3;

import java.util.Scanner;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 22/06/2022
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Single producer-consumer: 1;  Multiple producer-consumer: 2;");
        switch (scanner.next()) {
            case "1":
                checkSingleProducerConsumer();
                break;
            case "2":
                checkMultipleProducerConsumer();
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    private static void checkSingleProducerConsumer() throws InterruptedException {
        MessageBus messageBus = new MessageBus(5);
        Thread producer = new Thread(new Producer(messageBus), "Thread-Producer");
        Thread consumer = new Thread(new Consumer(messageBus), "Thread-Consumer");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }

    private static void checkMultipleProducerConsumer() throws InterruptedException {
        MessageBus messageBus = new MessageBus(5);
        Thread firstProducer = new Thread(new Producer(messageBus), "Thread-Producer-1");
        Thread secondProducer = new Thread(new Producer(messageBus), "Thread-Producer-2");
        Thread thirdProducer = new Thread(new Producer(messageBus), "Thread-Producer-3");
        Thread firstConsumer = new Thread(new Consumer(messageBus), "Thread-Consumer-1");
        Thread secondConsumer = new Thread(new Consumer(messageBus), "Thread-Consumer-2");
        Thread thirdConsumer = new Thread(new Consumer(messageBus), "Thread-Consumer-3");

        firstProducer.start();
        secondProducer.start();
        thirdProducer.start();
        firstConsumer.start();
        secondConsumer.start();
        thirdConsumer.start();

        firstProducer.join();
        secondProducer.join();
        thirdProducer.join();
        firstConsumer.join();
        secondConsumer.join();
        thirdConsumer.join();
    }
}
