package be.howest.ti.mars.webserver;

import be.howest.ti.mars.logic.controller.MarsController;
import be.howest.ti.mars.logic.domain.*;
import io.vertx.ext.web.RoutingContext;

import java.time.LocalDate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

class MarsOpenApiBridge implements MarsOpenApiBridgeInterface{
    private final MarsController controller;
    private static final Logger LOGGER = Logger.getLogger(MarsOpenApiBridge.class.getName());

    MarsOpenApiBridge() {
        this.controller = new MarsController();
    }

    public Object getMessage(RoutingContext ctx) {
        return controller.getMessage();
    }

    @Override
    public List<User> getUsers(RoutingContext ctx) {
        LOGGER.info("getUsers");
        return Collections.emptyList();
    }

    @Override
    public List<Seed> getSeeds(RoutingContext ctx) {
        LOGGER.info("getSeeds");
        return Collections.emptyList();
    }

    @Override
    public List<Plant> getPlants(RoutingContext ctx) {
        LOGGER.info("getPlants");
        controller.getPlants();
        return Collections.emptyList();
    }

    @Override
    public List<Product> getHistory(RoutingContext ctx) {
        LOGGER.info("getHistory");
        return Collections.emptyList();
    }

    @Override
    public List<Subscription> getSubscriptions(RoutingContext ctx) {
        LOGGER.info("getSubscriptions");
        return Collections.emptyList();
    }

    @Override
    public Map<Tool, Integer> getTools(RoutingContext ctx) {
        LOGGER.info("getTools");
        return Collections.emptyMap();
    }

    @Override
    public List<Product> getBasket(RoutingContext ctx) {
        LOGGER.info("getBasket");
        return Collections.emptyList();
    }

    @Override
    public Location getLocation(RoutingContext ctx) {
        LOGGER.info("getLocation");
        return null;
    }

    @Override
    public List<Product> getFavorites(RoutingContext ctx) {
        LOGGER.info("getFavorites");
        return Collections.emptyList();
    }

    @Override
    public Boolean buyProduct(RoutingContext ctx) {
        LOGGER.info("buyProduct");
        return true;
    }

    @Override
    public Boolean setSubscription(RoutingContext ctx) {
        LOGGER.info("setSubscription");
        return true;
    }

    @Override
    public Boolean editUser(RoutingContext ctx) {
        LOGGER.info("editUser");
        return true;
    }

    @Override
    public Boolean createUser(RoutingContext ctx) {
        LOGGER.info("createUser");
        String firstname = ctx.getBodyAsJson().getString("firstname");
        String lastname = ctx.getBodyAsJson().getString("lastname");
        String email = ctx.getBodyAsJson().getString("email");
        String date = ctx.getBodyAsJson().getString("birthDay");
        String street = ctx.getBodyAsJson().getString("address");
        int number = ctx.getBodyAsJson().getInteger("number");
        String dome = ctx.getBodyAsJson().getString("dome");
        Product crop1 = new Seed(1, ctx.getBodyAsJson().getString("crop1"), -1,1 ,"NYI");
        Product crop2 = new Seed(1,ctx.getBodyAsJson().getString("crop2"),  -1,1 ,"NYI");
        Product crop3 = new Seed(1, ctx.getBodyAsJson().getString("crop3"), -1,1 ,"NYI");
        List<Product> products = controller.createFavorites(crop1,crop2,crop3);
        LocalDate newDate = controller.createDate(date);
        User user = new User(firstname, lastname, email, newDate, new Subscription(SubscriptionType.BASIC), new Address(street, number,dome), new Favorite(products));
        LOGGER.log(Level.WARNING, "User: {0} " , user);
        controller.createUser(user);
        return true;
    }
}
