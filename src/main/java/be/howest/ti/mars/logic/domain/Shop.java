package be.howest.ti.mars.logic.domain;

import be.howest.ti.mars.logic.exceptions.SeedException;

import java.util.LinkedList;
import java.util.List;

public class Shop {

    private List<Seed> seeds;

    public Shop() {
        this.seeds = new LinkedList<>();
    }

    public void addSeed(Seed seed) {
        seeds.add(seed);
    }

    public boolean buySeed(Seed seed, int quantity) {
        if (seed.getAmount() < quantity) {
            throw new SeedException("There are not enough seeds in stock.");
        } else {
            seeds.remove(seed);
        }
        return true;
    }
}
