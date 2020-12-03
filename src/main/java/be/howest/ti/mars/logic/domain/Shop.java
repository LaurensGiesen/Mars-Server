package be.howest.ti.mars.logic.domain;

import java.util.*;

public class Shop {
    private List<Plant> plants;
    private List<Seed> seeds;

    public Shop() {
        this.plants = new LinkedList<>();
        this.seeds = new LinkedList<>();
    }

    public void buyAndSellPlant(Plant plant, User buyer, User seller, int quantity) {
        int amountOfPlantsSeller = seller.getHarvest().getProducts().get(plant);
        int amountOfPlantsBuyer = buyer.getHarvest().getProducts().get(plant);
        if (seller.getHarvest().getProducts().get(plant) == null) {
            throw new IllegalStateException("seller doesn't have the plant");
        }
        if (quantity >= amountOfPlantsSeller) {
            throw new IllegalStateException("seller hasn't enough plants");
        }
        seller.getHarvest().getProducts().replace(plant, amountOfPlantsSeller - quantity);
        buyer.getHarvest().getProducts().replace(plant, amountOfPlantsBuyer + quantity);
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public List<Seed> getSeeds() {
        return seeds;
    }
}
