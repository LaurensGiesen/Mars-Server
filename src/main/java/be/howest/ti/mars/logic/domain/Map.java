package be.howest.ti.mars.logic.domain;

import java.util.LinkedList;
import java.util.List;

public class Map {
    private List<User> users;
    private List<Plant> plants;
    private Shop shop;

    public Map() {
        this.users = new LinkedList<>();
        this.plants = new LinkedList<>();
        this.shop = new Shop();
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    public void addUser(User user) {
        users.add(user);
    }
    
    public Plant getPlant(Plant plant) {
        return plant;
    }
}
