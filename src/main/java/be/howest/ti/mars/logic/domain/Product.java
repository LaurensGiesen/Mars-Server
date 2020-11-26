package be.howest.ti.mars.logic.domain;


import java.util.Objects;

public abstract class Product {
    private int productId;
    private final String name;
    private final double price;
    private final int amount;

    public Product(int productId, String name, double price, int amount) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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
