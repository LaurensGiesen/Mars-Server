package be.howest.ti.mars.logic.domain;

import java.util.LinkedList;
import java.util.List;

public class Favorite {
    private List<Product> favorites;
    private int favoriteId;

    public Favorite(int favoriteId) {
        this.favorites = new LinkedList<>();
    }
}
