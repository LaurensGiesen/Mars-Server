package be.howest.ti.mars.logic.domain;

import java.util.HashMap;
import java.util.Map;

public abstract class ProductCollection {

    private final int id;
    private final java.util.Map<Product, Integer> products;

    public ProductCollection(int id, java.util.Map<Product, Integer> products) {
        this.id = id;
        this.products = products;
    }

    public ProductCollection() {
        this(-1, new HashMap<>());
    }

    public int getId() {
        return id;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

}
