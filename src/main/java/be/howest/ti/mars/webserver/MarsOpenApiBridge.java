package be.howest.ti.mars.webserver;

import be.howest.ti.mars.logic.controller.MarsController;
import be.howest.ti.mars.logic.domain.*;
import io.vertx.ext.web.RoutingContext;

import java.sql.Date;
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
        String number = ctx.getBodyAsJson().getString("number");
        String dome = ctx.getBodyAsJson().getString("dome");
        User user = new User(firstname, lastname, email, new Date(267265), new Subscription(SubscriptionType.BASIC), new Location(dome,number + ""));
        LOGGER.log(Level.WARNING, user.toString());
        return true;
    }
}
