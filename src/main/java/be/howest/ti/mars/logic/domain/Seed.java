package be.howest.ti.mars.logic.domain;

public class Seed extends Product{

    private final String type;

    public Seed(int id, String name, double price, int amount, String type) {
        super(id, name, price, amount);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return super.getName();
    }
}
