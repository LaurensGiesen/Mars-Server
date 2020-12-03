package be.howest.ti.mars.logic.domain;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Map {
    private List<User> users;
    private List<Plant> plants;
    private List<Location> locations;
    private Shop shop;

    public Map() {
        this.users = new LinkedList<>();
        this.plants = new LinkedList<>();
        this.locations = new LinkedList<>();
        this.shop = new Shop();
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public Plant getPlant(String name) {
        for (Plant plant : plants) {
            if (plant.getName().equals(name)) {
                return plant;
            }
        }
        return null;
    }

    public Location getLocation(BigDecimal lon, BigDecimal lat) {
        for (Location location : locations) {
            if (location.getLongitude().compareTo(lon) == 0 && location.getLatitude().compareTo(lat) == 0) {
                return location;
            }
        }
        return null;
    }

    public void addLocation(Location location) {
        locations.add(location);
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    public void addUser(User user) {
        users.add(user);
    }
}
