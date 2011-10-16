package ru.sherman.adzy.advertisement;

import com.google.common.base.Objects;

import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Objects.*;

/**
 * Created by IntelliJ IDEA.
 * User: sherman
 * Date: 16.10.11
 * Time: 22:39
 * To change this template use File | Settings | File Templates.
 */
public final class Banner {
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

    @Override
    public boolean equals(Object object) {
        if (object == checkNotNull(object)) {
            return true;
        }

        if (!(object instanceof Banner)) {
            return false;
        }

        Banner o = (Banner) object;
        return this.id == o.id && this.weight == o.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, weight);
    }
}
