package be.howest.ti.mars.logic.domain;

public class Seed{

    private final int id;
    private final String name;
    private final double price;
    private final double weight;
    private final String type;


    public Seed(int id, String name, double price, double weight, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }
}
