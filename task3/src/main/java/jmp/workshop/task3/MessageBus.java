package jmp.workshop.task3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 23/06/2022
 */
public class MessageBus {

    private final Random random = new Random();
    private final Queue<Message> messageQueue = new LinkedList<>();
    private final int capacity;

    private static final Object EMPTY = new Object();
    private static final Object FULL = new Object();

    public MessageBus(int capacity) {
        this.capacity = capacity;
    }

    public void produce(Message message) throws InterruptedException {
        if (isFull()) {
            waitOnFull();
        }
        synchronized (this) {
            messageQueue.add(message);
            notifyAllForEmpty();
            Thread.sleep(random.nextInt(1000));
        }
    }

    public Message consume() throws InterruptedException {
        while (isEmpty()) {
            waitOnEmpty();
        }
        synchronized (this) {
            Message message = messageQueue.poll();
            notifyAllForFull();
            Thread.sleep(random.nextInt(1000));
            return message;
        }
    }

    private synchronized boolean isEmpty() {
        return messageQueue.isEmpty();
    }

    private synchronized boolean isFull() {
        return messageQueue.size() == capacity;
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
