package jmp.workshop.task2;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 21/06/2022
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        List<Integer> list = new ArrayList<>();
        Object lock = new Object();

        Thread firstThread = new Thread(new FirstThread(list, lock));
        Thread secondThread = new Thread(new SecondThread(list, lock));
        Thread thirdThread = new Thread(new ThirdThread(list, lock));

        firstThread.start();
        secondThread.start();
        thirdThread.start();

        firstThread.join();
        secondThread.join();
        thirdThread.join();
    }

}
