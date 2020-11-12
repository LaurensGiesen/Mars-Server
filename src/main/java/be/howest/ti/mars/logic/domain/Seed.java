package be.howest.ti.mars.logic.domain;

import java.util.Objects;

public class Seed {

    private final String name;
    private final int price;

    public Seed(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
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
