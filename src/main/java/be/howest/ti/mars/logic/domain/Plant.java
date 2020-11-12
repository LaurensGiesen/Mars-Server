package be.howest.ti.mars.logic.domain;

import java.util.Objects;

public class Plant {
    private final String name;
    private final int price;

    public Plant(String name, int price) {
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
        Plant plant = (Plant) o;
        return name.equals(plant.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
