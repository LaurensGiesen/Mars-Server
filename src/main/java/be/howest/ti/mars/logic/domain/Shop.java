package be.howest.ti.mars.logic.domain;

import java.util.List;

public class Shop {
    private List<Plant> plants;
    private List<Seed> seeds;
    public Shop(List<Plant> plants, List<Seed> seeds) {
        this.plants = plants;
        this.seeds = seeds;
    }

    public void buyAndSellPlant(Plant plant, User buyer, User seller, int quantity) {
        for (Plant plant1 : seller.getHarvest().keySet()) {
            if (plant1.equals(plant)) {
               Integer totalOfSeller = seller.getHarvest().get(plant);
               if (totalOfSeller >= quantity) {
                   Integer newTotalOfSeller = totalOfSeller - quantity;
                   seller.getHarvest().replace(plant,newTotalOfSeller);
                   Integer totalOfBuyer = buyer.getHarvest().get(plant);
                   Integer newTotalOfBuyer = totalOfBuyer + quantity;
                   buyer.getHarvest().replace(plant,newTotalOfBuyer);
               }
               throw new IllegalStateException("seller hasn't enough quantity");
            }
            throw new IllegalStateException("seller doesn't have the plant");
        }
    }
}
