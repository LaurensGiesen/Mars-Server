package be.howest.ti.mars.logic.domain;

import java.util.*;

public class Shop {
    private List<Plant> plants;
    private List<Seed> seeds;

    public Shop(List<Plant> plants, List<Seed> seeds) {
        this.plants = plants;
        this.seeds = seeds;
    }

    public void buyAndSellPlant(Plant plant, User buyer, User seller, int quantity) {
        int amountOfPlantsSeller = seller.getHarvest().get(plant);
        int amountOfPlantsBuyer = buyer.getHarvest().get(plant);
        if (seller.getHarvest().get(plant) == null) {
            throw new IllegalStateException("seller doesn't have the plant");
        }
        if (quantity >= amountOfPlantsSeller) {
            throw new IllegalStateException("seller hasn't enough plants");
        }
        seller.getHarvest().replace(plant, amountOfPlantsSeller - quantity);
        buyer.getHarvest().replace(plant, amountOfPlantsBuyer + quantity);
    }
}
