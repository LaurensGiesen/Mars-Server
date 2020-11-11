package be.howest.ti.mars.logic.domain;

import java.util.Objects;

public class Plant {
    private final String name;
    private final int price;
    private int count;

    public Plant(String name, int price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public Plant(String name, int price) {
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
        Plant plant = (Plant) o;
        return name.equals(plant.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
