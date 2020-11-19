package be.howest.ti.mars.logic.domain;

import java.util.Objects;

public class Tool {
    private double rentPrice;
    private final String name;
    private final String description;

    public Tool(double rentPrice, String name, String description) {
        this.rentPrice = rentPrice;
        this.name = name;
        this.description = description;
    }

    public double getRentPrice() {
        return rentPrice;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setRentPrice(double rentPrice) {
        this.rentPrice = rentPrice;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Tool tool = (Tool) object;
        return name.equals(tool.name);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
