package ru.sherman.adzy.util;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: sherman
 * Date: 16.10.11
 * Time: 23:26
 * To change this template use File | Settings | File Templates.
 */
public class Randoms {
    private Randoms() {}

    public static Interval<Integer> getRandomInterval(Integer max) {
        Random rand = new Random();

        int randomValue = 0;
        do {
            randomValue = rand.nextInt(max);
        } while (randomValue == 0);

        return new Interval<Integer>(randomValue, randomValue);
    }
}
