package be.howest.ti.mars.logic.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Basket {
    private List<Product> products;
    private int basketId;
    private int totalPrice;

    public Basket() {
        this.products = new LinkedList<>();
        this.basketId = nextBasketId();
        this.totalPrice = 0;
    }

    private int nextBasketId() {
        basketId++;
        return basketId;
    }

    public void addProductToBasket(Product product) {
        products.add(product);
    }

    public void removeProductFromBasket(Product product) {
        products.remove(product);
    }

    public void calculateTotalPrice() {
        for ( Product product : products ) {
            totalPrice += product.getPrice();
        }
    }

    @Override
    public String toString() {
        return "Basket { " +
                "basketId = " + basketId +
                ", products = " + products +
                ", totalPrice = € " + totalPrice + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return basketId == basket.basketId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(basketId);
    }
}
