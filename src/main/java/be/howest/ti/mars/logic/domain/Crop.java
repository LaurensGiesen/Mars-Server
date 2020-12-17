package be.howest.ti.mars.logic.domain;

import java.util.Objects;

public class Crop {

    private final String name;
    private final String type;

    public Crop(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crop crop = (Crop) o;
        return Objects.equals(name, crop.name) && Objects.equals(type, crop.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
