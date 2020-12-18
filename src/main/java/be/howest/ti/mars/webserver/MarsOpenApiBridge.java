package be.howest.ti.mars.webserver;

import be.howest.ti.mars.logic.controller.MarsController;
import be.howest.ti.mars.logic.domain.*;
import io.vertx.ext.web.RoutingContext;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

class MarsOpenApiBridge implements MarsOpenApiBridgeInterface {
    private static final String AMOUNT = "amount";
    private final MarsController controller;
    private static final Logger LOGGER = Logger.getLogger(MarsOpenApiBridge.class.getName());

    private static final String USER_ID = "userId";
    private static final String PRODUCT_ID = "productId";
    private static final String PRODUCT_TYPE = "productType";

    MarsOpenApiBridge() {
        this.controller = new MarsController();
    }

    public Object getMessage(RoutingContext ctx) {
        ctx.failed();
        return controller.getMessage();
    }

    public User getUser(RoutingContext ctx) {
        LOGGER.info("getUsers");
        int id = Integer.parseInt(ctx.request().getParam(USER_ID));
        return controller.getUserById(id);
    }

    @Override
    public List<Product> getSeeds(RoutingContext ctx) {
        LOGGER.info("getSeeds");
        return controller.getProduct(ProductType.SEED);
    }

    @Override
    public List<Product> getPlants(RoutingContext ctx) {
        LOGGER.info("getPlants");
        return controller.getProduct(ProductType.PLANT);
    }

    @Override
    public List<Product> getBasket(RoutingContext ctx) {
        int userId = Integer.parseInt(ctx.request().getParam(USER_ID));
        return controller.getBasket(userId);

    }

    @Override
    public List<Product> getFavorites(RoutingContext ctx) {
        LOGGER.info("getFavorites");
        int userId = Integer.parseInt(ctx.request().getParam(USER_ID));
        return controller.getFavorites(userId);
    }

    @Override
    public Boolean createUser(RoutingContext ctx) {
        LOGGER.info("createUser");
        try {
            String firstname = ctx.getBodyAsJson().getString("firstname");
            String lastname = ctx.getBodyAsJson().getString("lastname");
            String email = ctx.getBodyAsJson().getString("email");
            String date = ctx.getBodyAsJson().getString("birthDay");
            String street = ctx.getBodyAsJson().getString("address");
            int number = ctx.getBodyAsJson().getInteger("number");
            String dome = ctx.getBodyAsJson().getString("dome");
            Product crop1 = controller.getSeedByName(ctx.getBodyAsJson().getString("crop1"));
            Product crop2 = controller.getSeedByName(ctx.getBodyAsJson().getString("crop2"));
            Product crop3 = controller.getSeedByName(ctx.getBodyAsJson().getString("crop3"));
            List<Product> products = controller.createFavorites(crop1, crop2, crop3);
            LocalDate newDate = controller.createDate(date);
            int addressId = controller.addAddress(street, number, dome);
            int id = controller.createUser(firstname, lastname, email, newDate, SubscriptionType.PREMIUM, addressId);
            controller.addFavoriteToUser(id, products, 1);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean addProduct(RoutingContext ctx) {
        LOGGER.info("addProduct");
        String name = ctx.getBodyAsJson().getString("name");
        Double price = ctx.getBodyAsJson().getDouble("price");
        int ownerId = ctx.getBodyAsJson().getInteger("ownerId");
        String date = ctx.getBodyAsJson().getString("date");
        int amount = ctx.getBodyAsJson().getInteger(AMOUNT);
        String image = ctx.getBodyAsJson().getString("image");
        String type = ctx.getBodyAsJson().getString("type");
        return controller.createProduct(name, price, ownerId, date, amount, image, type);
    }

    public boolean addProductToFavorite(RoutingContext ctx) {
        int userId = ctx.getBodyAsJson().getInteger(USER_ID); // <----
        int productId = ctx.getBodyAsJson().getInteger(PRODUCT_ID);
        String productType = ctx.getBodyAsJson().getString(PRODUCT_TYPE);
        int amount = ctx.getBodyAsJson().getInteger(AMOUNT);
        return controller.addProductToFavorite(userId, productId, productType, amount);
    }

    public Boolean addProductToBasket(RoutingContext ctx) {
        int userId = ctx.getBodyAsJson().getInteger(USER_ID);
        int productId = ctx.getBodyAsJson().getInteger(PRODUCT_ID);
        String productType = ctx.getBodyAsJson().getString(PRODUCT_TYPE);
        int amount = ctx.getBodyAsJson().getInteger(AMOUNT);
        return controller.addProductToBasket(userId, productId, productType, amount);
    }


    public Boolean removeProductFromFavorite(RoutingContext ctx) {
        int userId = ctx.getBodyAsJson().getInteger(USER_ID);
        int productId = ctx.getBodyAsJson().getInteger(PRODUCT_ID);
        String productType = ctx.getBodyAsJson().getString(PRODUCT_TYPE);
        int amount = ctx.getBodyAsJson().getInteger(AMOUNT);
        return controller.removeProductFromFavorite(userId, productId, productType, amount);
    }

    public Boolean removeProductFromBasket(RoutingContext ctx) {
        int userId = ctx.getBodyAsJson().getInteger(USER_ID);
        int productId = ctx.getBodyAsJson().getInteger(PRODUCT_ID);
        String productType = ctx.getBodyAsJson().getString(PRODUCT_TYPE);
        int amount = ctx.getBodyAsJson().getInteger(AMOUNT);
        return controller.removeProductFromBasket(userId, productId, productType, amount);
    }

    public Boolean removeProduct(RoutingContext ctx) {
        int userId = ctx.getBodyAsJson().getInteger(USER_ID);
        int productId = ctx.getBodyAsJson().getInteger(PRODUCT_ID);
        String productType = ctx.getBodyAsJson().getString(PRODUCT_TYPE);
        return controller.removeProduct(userId, productId, productType);
    }

    @Override
    public List<CropTypes> getCropTypes(RoutingContext ctx) {
        LOGGER.info("getCropTypes");
        int id = Integer.parseInt(ctx.request().getParam(USER_ID));
        return controller.getAllCrops(id);
    }

    public List<CropTypes> getCropsWhereNameIsLike(RoutingContext ctx) {
        String partOfName = ctx.request().getParam("name");
        int id = Integer.parseInt(ctx.request().getParam(USER_ID));
        return controller.getCropsWhereNameIsLike(partOfName, id);
    }

    public List<CropTypes> getCropByLocation(RoutingContext ctx) {
        double longitude = Double.parseDouble(ctx.request().getParam("longitude"));
        double latitude = Double.parseDouble(ctx.request().getParam("latitude"));
        int id = Integer.parseInt(ctx.request().getParam(USER_ID));
        return controller.getCropByLocation(longitude, latitude, id);
    }

    public Boolean updateUser(RoutingContext ctx) {
        try {
            String firstname = ctx.getBodyAsJson().getString("firstname");
            String lastname = ctx.getBodyAsJson().getString("lastname");
            String email = ctx.getBodyAsJson().getString("email");
            String date = ctx.getBodyAsJson().getString("birthDay");
            String street = ctx.getBodyAsJson().getString("address");
            int number = ctx.getBodyAsJson().getInteger("number");
            String dome = ctx.getBodyAsJson().getString("dome");
            int id = Integer.parseInt(ctx.request().getParam(USER_ID));
            LocalDate newDate = controller.createDate(date);
            controller.updateAddress(street, number, dome, getUserById(id).getAddress().getId());
            return controller.updateUser(firstname, lastname, email, newDate, id);
        } catch (Exception ex) {
            return false;
        }
    }

    public User getUserById(int id) {
        return controller.getUserById(id);
    }

    public Boolean updateSubscription(RoutingContext ctx) {
        int userId = Integer.parseInt(ctx.request().getParam(USER_ID));
        int subscriptionId = Integer.parseInt(ctx.request().getParam("subscriptionId"));
        return controller.updateSubscription(userId, subscriptionId);
    }

    public List<Crop> getCrops(RoutingContext ctx) {
        ctx.failed();
        return controller.getCropNames();
    }
}
