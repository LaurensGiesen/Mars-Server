package be.howest.ti.mars.logic.controller;

import be.howest.ti.mars.logic.data.DatabaseUsersRepository;
import be.howest.ti.mars.logic.data.MarsRepository;
import be.howest.ti.mars.logic.domain.Product;
import be.howest.ti.mars.logic.domain.User;
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
        products.add(crop1);
        products.add(crop2);
        products.add(crop3);
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
}
