package jmp.workshop.task1;

import jmp.workshop.task1.customhashmap.CustomHashMap;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

import static java.util.Objects.nonNull;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 21/06/2022
 */
public class FirstThread implements Runnable {

    private final Random random = new Random();
    private Map<Integer, Integer> map;
    private CustomHashMap<Integer, Integer> customHashMap;

    private final Integer limit;

    public FirstThread(Map<Integer, Integer> map, Integer limit) {
        this.map = map;
        this.limit = limit;
    }

    public FirstThread(CustomHashMap<Integer, Integer> map, Integer limit) {
        this.customHashMap = map;
        this.limit = limit;
    }

    @Override
    public void run() {
        if (nonNull(map)) {
            for (int i = 0; i < limit; i++) {
                Integer value = random.nextInt();
                map.put(i, value);
                System.out.printf("(%s,%s) is added to the Map\n", i, value);
            }
        } else if (nonNull(customHashMap)) {
            for (int i = 0; i < limit; i++) {
                Integer value = random.nextInt();
                customHashMap.put(i, value);
                System.out.printf("(%s,%s) is added to the Map\n", i, value);
            }
        }
    }
}
