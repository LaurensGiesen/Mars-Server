package be.howest.ti.mars.logic.domain;

import be.howest.ti.mars.logic.exceptions.ProductException;

import java.util.LinkedList;
import java.util.List;

public class Shop {

    private List<Product> seeds;

    public Shop() {
        this.seeds = new LinkedList<>();
    }

    public void addSeed(Product seed) {
        seeds.add(seed);
    }

    public boolean buySeed(Product seed, int quantity) {
        if (seed.getAmount() < quantity) {
            throw new ProductException("There are not enough seeds in stock.");
        } else {
            seeds.remove(seed);
        }
        return true;
    }
}
