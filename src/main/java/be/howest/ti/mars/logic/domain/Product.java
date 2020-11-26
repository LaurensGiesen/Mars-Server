package be.howest.ti.mars.logic.domain;


import java.util.Objects;

public abstract class Product {
    private final int product_id;
    private final String name;
    private final double price;
    private final int amount;

    public Product(int product_id, String name, double price, int amount) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
