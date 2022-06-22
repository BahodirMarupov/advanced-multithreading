package jmp.workshop.task3;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 22/06/2022
 */
public class Consumer implements Runnable {

    private final MessageBus messageBus;

    public Consumer(MessageBus messageBus) {
        this.messageBus = messageBus;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = messageBus.consume();
                System.out.println("Consumed message : " + message);
            } catch (InterruptedException e) {
                System.out.printf("Exception in %s : %s\n", Thread.currentThread().getName(), e.getMessage());
            }
        }
    }
}
