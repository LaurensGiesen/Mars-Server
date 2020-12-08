package be.howest.ti.mars.logic.domain;

import java.util.LinkedList;
import java.util.List;

public class MarketPlace {

    private List<Product> plants;
    private List<Product> seeds;

    public MarketPlace() {
        this.plants = new LinkedList<>();
        this.seeds = new LinkedList<>();
    }

    public void buyAndSellPlant(Product plant, User buyer, User seller, int quantity) {
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

    public List<Product> getPlants() {
        return plants;
    }

    public List<Product> getSeeds() {
        return seeds;
    }

}
