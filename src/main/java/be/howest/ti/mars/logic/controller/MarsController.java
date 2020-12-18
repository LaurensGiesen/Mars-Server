package be.howest.ti.mars.logic.controller;

import be.howest.ti.mars.logic.data.MarsRepository;
import be.howest.ti.mars.logic.domain.*;
import be.howest.ti.mars.logic.exceptions.ProductException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarsController {

    private static final Logger LOGGER = Logger.getLogger(MarsController.class.getName());
    private static final String MSG = "Hello, Mars!";
    MarsRepository repo = MarsRepository.getInstance();

    public String getMessage() {
        return MSG;
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

    public int createUser(String firstname, String lastname, String email, LocalDate newDate, SubscriptionType subscriptionType, int addressId) {
        return repo.createUser(firstname, lastname, email, newDate, subscriptionType, addressId);
    }

    public void addFavoriteToUser(int id, List<Product> products, int amount) {
        repo.addFavoriteToUser(id, products, amount);
    }

    public boolean addProductToFavorite(int userId, int productId, String productType, int amount) {
        return repo.addProductToFavorite(userId, productId, productType, amount);
    }

    public List<Product> getFavorites(int userId) {
        return repo.getFavorites(userId);
    }

    public Boolean addProductToBasket(int userId, int productId, String productType, int amount) {
        return repo.addProductToBasket(userId, productId, productType, amount);
    }

    public List<Product> getBasket(int id) {
        return repo.getBasket(id);
    }

    public Boolean removeProductFromFavorite(int userId, int productId, String productType, int amount) {
        return repo.removeProductFromFavorite(userId, productId, productType, amount);
    }

    public Boolean removeProductFromBasket(int userId, int productId, String productType, int amount) {
        return repo.removeProductFromBasket(userId, productId, productType, amount);
    }

    public Boolean removeProduct(int userId, int productId, String productType) {
        return repo.removeProduct(userId, productId, productType);
    }

    public List<CropTypes> getAllCrops(int id) {
        return repo.getCropTypes(getUserById(id).getSubscription().getType());
    }

    public List<CropTypes> getCropsWhereNameIsLike(String partOfName, int id) {
        return repo.getCropsWhereNameIsLike(partOfName, getUserById(id).getSubscription().getType());
    }

    public List<CropTypes> getCropByLocation(double longitude, double latitude, int id) {
        return repo.getCropByLocation(longitude, latitude, getUserById(id).getSubscription().getType());
    }

    public int addAddress(String street, int number, String dome) {
        return repo.addAddress(street, number,dome);
    }

    public Boolean updateUser(String firstname, String lastname, String email, LocalDate newDate, int id) {
        return repo.updateUser(firstname, lastname, email, newDate, id);
    }

    public void updateAddress(String street, int number, String dome, int id) {
        repo.updateAddress(street, number, dome, id);
    }

    public User getUserById(int id) {
        return repo.getUserById(id);
    }

    public Boolean updateSubscription(int userId, int subscriptionId) {
        return repo.updateSubscription(userId, subscriptionId);
    }

    public List<Crop> getCropNames() {
        return  repo.getCropNames();
    }

    public Product getProductById(int productId) {
        for (Product product: getProduct(ProductType.PLANT)){
            if (product.getProductId() == productId){
                return product;
            }
        }
        return null;
    }
}
