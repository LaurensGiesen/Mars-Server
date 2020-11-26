package be.howest.ti.mars.logic.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Favorite {
    private List<Product> favorites;
    private int favoriteId;

    public Favorite(int favoriteId) {
        this.favorites = new LinkedList<>();
    }

    public void removeFromFavorites(Product product) {
        favorites.remove(product);
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "favorites=" + favorites +
                ", favoriteId=" + favoriteId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorite favorite = (Favorite) o;
        return favoriteId == favorite.favoriteId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(favoriteId);
    }
}
