package be.howest.ti.mars.logic.controller;

import be.howest.ti.mars.logic.data.MarsRepository;
import be.howest.ti.mars.logic.domain.*;
import be.howest.ti.mars.logic.exceptions.ProductException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarsController {

    private static final Logger LOGGER = Logger.getLogger(MarsController.class.getName());

    MarsRepository repo = MarsRepository.getInstance();

    public String getMessage() {
        return "Hello, Mars!";
    }

    public List<Product> createFavorites(Product crop1, Product crop2, Product crop3) {
        List<Product> products = new LinkedList<>();
        if (crop1 != null) {
            products.add(crop1);
        }
        if (crop2 != null) {
            products.add(crop2);
        }
        if (crop3 != null) {
            products.add(crop3);
        }
        return products;
    }

    public LocalDate createDate(String date) {
        String[] split = date.split("-");
        return LocalDate.of(Integer.parseInt(split[2]), Integer.parseInt(split[1]), Integer.parseInt(split[0]));
    }

    public List<Product> getProduct(ProductType type) {
        return repo.getProduct(type);
    }

    public boolean createProduct(String name, Double price, int ownerId, String date, int amount, String image, String type) {
        LocalDate date1 = createDate(date);
        User owner = repo.getUserById(ownerId);
        if (type.equals("seed")) {
            repo.createProduct(name, price, owner, date1, amount, image, ProductType.SEED);
        } else if (type.equals("plant")) {
            repo.createProduct(name, price, owner, date1, amount, image, ProductType.PLANT);
        } else {
            LOGGER.log(Level.SEVERE, "Invalid Product Type");
            throw new ProductException();
        }
        return true;
    }

    public Product getSeedByName(String crop1) {
        return repo.getSeedByName(crop1);
    }

    public int createUser(String firstname, String lastname, String email, LocalDate newDate, Subscription subscription, Address address) {
        return repo.createUser(firstname, lastname, email, newDate, subscription, address);
    }

    public void addFavoriteToUser(int id, List<Product> products) {
        repo.addFavoriteToUser(id, products);
    }

    public boolean addProductToFavorite(int userId, int productId, String productType) {
        return repo.addProductToFavorite(userId, productId, productType);
    }

    public List<Product> getFavorites(int userId) {
        return repo.getFavorites(userId);
    }

    public Boolean addProductToBasket(int userId, int productId, String productType) {
        return repo.addProductToBasket(userId, productId, productType);
    }
}
