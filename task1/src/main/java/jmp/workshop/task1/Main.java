package jmp.workshop.task1;

import jmp.workshop.task1.customhashmap.CustomHashMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 21/06/2022
 */
public class Main {

    public static final Integer LIMIT = 5000;

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose map implementation.");
        System.out.println("HashMap: 1;  Synchronized HashMap: 2;  ConcurrentHashMap: 3;  CustomMap 4;");

        switch (Optional.ofNullable(scanner.next()).orElse("")) {
            case "1":
                checkWithHashMap();
                break;
            case "2":
                checkWithSynchronizedHashMap();
                break;
            case "3":
                checkWithConcurrentHashMap();
                break;
            case "4":
                checkWithCustomMap();
                break;
            default:
                System.err.println("Invalid input!");
        }
    }

    private static void checkWithHashMap() throws InterruptedException {
        Map<Integer, Integer> map = new HashMap<>();
        Thread firstThread = new Thread(new FirstThread(map, LIMIT));
        Thread secondThread = new Thread(new SecondThread(map, LIMIT));

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();
    }

    private static void checkWithSynchronizedHashMap() throws InterruptedException {
        Map<Integer, Integer> map = Collections.synchronizedMap(new HashMap<>());
        Thread firstThread = new Thread(new FirstThread(map, LIMIT));
        Thread secondThread = new Thread(new SecondThread(map, LIMIT));

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();
    }

    private static void checkWithConcurrentHashMap() throws InterruptedException {
        Map<Integer, Integer> map = new ConcurrentHashMap<>();
        Thread firstThread = new Thread(new FirstThread(map, LIMIT));
        Thread secondThread = new Thread(new SecondThread(map, LIMIT));

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();
    }

    private static void checkWithCustomMap() throws InterruptedException {
        CustomHashMap<Integer, Integer> map = new CustomHashMap<>();
        Thread firstThread = new Thread(new FirstThread(map, LIMIT), "Thread-1");
        Thread secondThread = new Thread(new SecondThread(map, LIMIT), "Thread-2");

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();
    }
}
