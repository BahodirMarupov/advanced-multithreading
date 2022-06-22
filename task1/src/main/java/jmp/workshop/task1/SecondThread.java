package jmp.workshop.task1;

import jmp.workshop.task1.customhashmap.CustomHashMap;

import java.util.Map;
import java.util.Objects;

import static java.util.Objects.nonNull;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 21/06/2022
 */
public class SecondThread implements Runnable {

    private Map<Integer, Integer> map;
    private CustomHashMap<Integer, Integer> customHashMap;

    private final Integer limit;

    public SecondThread(Map<Integer, Integer> map, Integer limit) {
        this.map = map;
        this.limit = limit;
    }

    public SecondThread(CustomHashMap<Integer, Integer> map, Integer limit) {
        this.customHashMap = map;
        this.limit = limit;
    }

    @Override
    public void run() {
        if (nonNull(map)) {
            for (int i = 0; i < limit; i++) {
                int sum = map.values().stream().mapToInt(value -> value).sum();
                System.out.println("Sum of map values: " + sum);
            }
        } else if (nonNull(customHashMap)) {
            for (int i = 0; i < limit; i++) {
                int sum = customHashMap.values().stream().mapToInt(value -> value).sum();
                System.out.println("Sum of map values: " + sum);
            }
        }
    }
}
