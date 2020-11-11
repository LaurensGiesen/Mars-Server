package be.howest.ti.mars.logic.domain;

import java.util.Objects;

public class Seed {

    private final String name;
    private final int price;
    private int count;

    public Seed(String name, int price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public Seed(String name, int price) {
        this(name, price, 0);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public void increment() {
        count++;
    }

    public void decrement() {
        count--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seed seed = (Seed) o;
        return name.equals(seed.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
