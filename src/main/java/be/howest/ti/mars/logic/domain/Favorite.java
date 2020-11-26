package be.howest.ti.mars.logic.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Favorite {
    private final List<Product> favorites;

    public Favorite(List<Product> favorites) {
        this.favorites = favorites;
    }

    public Favorite() {
        this.favorites = new LinkedList<>();
    }

    public void removeFromFavorites(Product product) {
        favorites.remove(product);
    }

    public List<Product> getFavorites() {
        return favorites;
    }
}
