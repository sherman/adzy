package ru.sherman.adzy.advertisement;

/**
 * Created by IntelliJ IDEA.
 * User: sherman
 * Date: 16.10.11
 * Time: 22:39
 * To change this template use File | Settings | File Templates.
 */
public class Banner {
    private final int weight;
    private final int id;

    public Banner(int id, int weight) {
        this.id = id;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public int getId() {
        return id;
    }
}
