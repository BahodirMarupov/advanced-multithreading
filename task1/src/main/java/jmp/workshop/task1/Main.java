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

        Thread firstThread;
        Thread secondThread;
        switch (Optional.ofNullable(scanner.next()).orElse("")) {
            case "1":
                Map<Integer, Integer> map = new HashMap<>();
                firstThread = new Thread(new FirstThread(map, LIMIT), "Thread-1");
                secondThread = new Thread(new SecondThread(map, LIMIT), "Thread-2");
                break;
            case "2":
                map = Collections.synchronizedMap(new HashMap<>());
                firstThread = new Thread(new FirstThread(map, LIMIT), "Thread-1");
                secondThread = new Thread(new SecondThread(map, LIMIT), "Thread-2");
                break;
            case "3":
                map = new ConcurrentHashMap<>();
                firstThread = new Thread(new FirstThread(map, LIMIT), "Thread-1");
                secondThread = new Thread(new SecondThread(map, LIMIT), "Thread-2");
                break;
            case "4":
                CustomHashMap<Integer, Integer> customMap = new CustomHashMap<>();
                firstThread = new Thread(new FirstThread(customMap, LIMIT), "Thread-1");
                secondThread = new Thread(new SecondThread(customMap, LIMIT), "Thread-2");
                break;
            default:
                throw new IllegalArgumentException("Invalid input!");
        }

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();
    }
}
