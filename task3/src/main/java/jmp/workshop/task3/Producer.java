package jmp.workshop.task3;

import java.util.Random;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 22/06/2022
 */
public class Producer implements Runnable {

    private Random random = new Random();
    private static int messageId = 0;

    private final MessageBus messageBus;

    public Producer(MessageBus messageBus) {
        this.messageBus = messageBus;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = generateMessage();
                messageBus.produce(message);
                System.out.println("Newly produced message : " + message);
            } catch (InterruptedException e) {
                System.out.printf("Exception in %s : %s\n", Thread.currentThread().getName(), e.getMessage());
            }
        }
    }

    private Message generateMessage() {
        return new Message(messageId++, random.nextDouble());
    }
}
