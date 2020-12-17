package be.howest.ti.mars.logic.domain;


import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.util.Objects;

public class Product {
    private final int productId;
    private final String name;
    private final double price;
    private final User owner;
    private final LocalDate date;
    private int amount;
    private final String image;
    private final ProductType type;

    public Product(int productId, String name, double price, User owner, LocalDate date, int amount, String image, ProductType type) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.owner = owner;
        this.date = date;
        this.amount = amount;
        this.image = image;
        this.type = type;
    }

    public Product(int productId, String name, double price, ProductType type) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.type = type;
        this.owner = null;
        this.amount = -1;
        this.image = null;
        this.date = null;
    }

    public int getProductId() {
        return productId;
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

    public User getOwner() {
        return owner;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }

    public ProductType getType() {
        return type;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
