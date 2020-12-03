package be.howest.ti.mars.logic.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Favorite {

    private final int id;
    private final List<Product> favorites;

    public Favorite(int id, List<Product> favorites) {
        this.id = id;
        this.favorites = favorites;
    }

    public Favorite(List<Product> favorites) {
        this(-1, favorites);
    }

    public Favorite() {
        this(-1, new LinkedList<>());
    }

    public void removeFromFavorites(Product product) {
        favorites.remove(product);
    }

    public List<Product> getFavorites() {
        return favorites;
    }

    public int getId() {
        return id;
    }
}
