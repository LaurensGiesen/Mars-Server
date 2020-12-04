package be.howest.ti.mars.logic.controller;

import be.howest.ti.mars.logic.data.DatabaseUsersRepository;
import be.howest.ti.mars.logic.data.MarsRepository;
import be.howest.ti.mars.logic.domain.Plant;
import be.howest.ti.mars.logic.domain.Product;
import be.howest.ti.mars.logic.domain.Seed;
import be.howest.ti.mars.logic.domain.User;
import be.howest.ti.mars.logic.exceptions.ProductException;
import be.howest.ti.mars.logic.exceptions.UsersException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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
        if (crop1 != null){
            products.add(crop1);
        }
        if (crop2 != null){
            products.add(crop2);
        }
        if (crop3 != null){
            products.add(crop3);
        }
        return products;
    }

    public LocalDate createDate(String date) {
        String[] split = date.split("-");
        return LocalDate.of(Integer.parseInt(split[2]), Integer.parseInt(split[1]), Integer.parseInt(split[0]));
    }

    public void createUser(User user) {
        repo.createUser(user);
    }

    public void getPlants() {
        repo.getPlants();
    }

    public boolean createProduct(int id, String name, Double price, int ownerId, String date, int amount, String image, String type) {
        LocalDate date1 = createDate(date);
        Product product;
        User owner = repo.getUserById(ownerId);
        if (type.equals("seed")) {
            product = new Seed(id, name, price, owner, date1, amount, image);
        } else if (type.equals("plant")){
            product = new Plant(id, name, price, owner, date1, amount, image);
        }else {
            LOGGER.log(Level.SEVERE, "Invalid Product Type");
            throw new ProductException();
        }
        repo.createProduct(product);
        return true;
    }

    public Product getSeedByName(String crop1) {
        return repo.getSeedByName(crop1);
    }
}
