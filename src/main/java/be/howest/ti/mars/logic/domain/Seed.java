package be.howest.ti.mars.logic.domain;

public class Seed extends Product{

    private final int id;
    private final double weight;
    private final String type;

    public Seed(int id, String name, double price, double weight, String type) {
        super(name, price);
        this.id = id;
        this.weight = weight;
        this.type = type;
    }

    public int getId() {
        return id;
    }


    public double getWeight() {
        return weight;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return super.getName();
    }
}
