package be.howest.ti.mars.logic.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Basket {

    private final List<Product> products;

    public Basket() {
        this.products = new LinkedList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return Objects.equals(products, basket.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products);
    }
}
