package be.howest.ti.mars.logic.domain;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasketTest {

    Product s1 = new Seed("TomatoSeed", 1);
    Product p1 = new Plant("Waterkers", 2);
    Basket basket = new Basket();

    @Test
    void addProductToBasket() {
        List<Product> addedProducts = new LinkedList<>();
        addedProducts.add(s1);
        basket.addProductToBasket(s1);
        assertEquals(addedProducts, basket.getProducts());
    }

    @Test
    void removeProductFromBasket() {
        addProductToBasket();
        basket.removeProductFromBasket(s1);
        assertEquals(Collections.emptyList(), basket.getProducts());
    }

    @Test
    void calculateTotalPrice() {
    }
}