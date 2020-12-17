package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.*;
import be.howest.ti.mars.logic.exceptions.ProductException;
import be.howest.ti.mars.logic.exceptions.UserException;
import org.h2.tools.Server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
MBL: this is only a starter class to use a H2 database.
To make this class useful, please complete it with the topics seen in the module OOA & SD
- Make sure the conf/config.json properties are correct.
- The h2 web console is available at http://localhost:9000
- The h2 database file is located at ~/mars-db
- Hint:
  - Mocking this repository is not needed. Create database creating and population script in plain SQL.
    Use the @Before or @Before each (depending on the type of test) to quickly setup a fully populated db.
 */
public class MarsRepository {

    private final DatabaseUsersRepository databaseUser = new DatabaseUsersRepository();
    private final DatabaseProductRepository databaseProduct = new DatabaseProductRepository();
    private final DatabaseMapRepository databaseMap = new DatabaseMapRepository();

    private static final Logger LOGGER = Logger.getLogger(DatabaseMapRepository.class.getName());


    private static final MarsRepository INSTANCE = new MarsRepository();
    private Server dbWebConsole;
    private String username;
    private String password;
    private String url;


    private MarsRepository() {
    }

    public static MarsRepository getInstance() {
        return INSTANCE;
    }

    public void cleanUp() {
        dbWebConsole.stop();
    }

    public static void configure(String url, String username, String password, int console)
            throws SQLException {
        INSTANCE.username = username;
        INSTANCE.password = password;
        INSTANCE.url = url;
        INSTANCE.dbWebConsole = Server.createWebServer(
                "-ifNotExists",
                "-webPort", String.valueOf(console)).start();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getInstance().url, getInstance().username, getInstance().password);
    }

    public User getUserById(int ownerId) {
        return databaseUser.getById(ownerId);
    }

    public Product getSeedByName(String crop1) {
        List<Product> seeds = databaseProduct.find(crop1, ProductType.SEED);
        if (seeds == null || seeds.isEmpty()) {
            return null;
        }
        return databaseProduct.find(crop1, ProductType.SEED).get(0);
    }

    public List<Product> getProduct(ProductType type) {
        return databaseProduct.getAll(type);
    }

    public void createProduct(String name, Double price, User owner, LocalDate date1, int amount, String image, ProductType type) {
        int id = databaseProduct.add(name, price, owner, date1, amount, image, type);
        createImage(image, id);
    }

    private void createImage(String image, int id) {
        System.out.println("kfjdslmk");
        System.out.println(image);
        String base64Image = image.split(",")[1];
        String extension = image.split("/")[1].split(";")[0];
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
        try (OutputStream stream = new FileOutputStream("images/" + id + "." + extension)) {
            stream.write(imageBytes);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed To Create Image");
            throw new ProductException("Failed To Create Image");
        }
    }

    public int createUser(String firstname, String lastname, String email, LocalDate newDate, SubscriptionType subscriptionType, int addressId) {
        return databaseUser.add(firstname, lastname, email, newDate, subscriptionType, addressId);
    }

    public void addFavoriteToUser(int id, List<Product> products, int amount) {
        databaseUser.addToFavorite(id, products, amount);
    }

    public boolean addProductToFavorite(int userId, int productId, String productType, int amount) {
        Product product = databaseProduct.getById(productId, productType);
        return databaseUser.updateProductOfUser(userId, product, amount, DatabaseUsersRepository.SQL_INSERT_FAVORITE);
    }

    public List<Product> getFavorites(int userId) {
        return databaseUser.getFavorites(userId);
    }

    public Boolean addProductToBasket(int userId, int productId, String productType, int amount) {
        Product product = databaseProduct.getById(productId, productType);
        return databaseUser.addToBasket(userId, product, amount);
    }

    public List<Product> getBasket(int id) {
        return databaseUser.getBasket(id);
    }

    public Boolean removeProductFromFavorite(int userId, int productId, String productType, int amount) {
        Product product = databaseProduct.getById(productId, productType);
        return databaseUser.removeProductFromFavorite(userId, product, amount);
    }

    public Boolean removeProductFromBasket(int userId, int productId, String productType, int amount) {
        Product product = databaseProduct.getById(productId, productType);
        return databaseUser.removeProductFromBasket(userId, product, amount);
    }

    public Boolean removeProduct(int userId, int productId, String productType) {
        Product product = databaseProduct.getById(productId, productType);
        if (product.getOwner().getId() != userId) {
            throw new UserException("You Can Not Remove A Product That Is Not Yours");
        }
        return databaseProduct.removeProduct(product);
    }

    public List<CropTypes> getCropTypes(SubscriptionType type) {
        return databaseMap.getAllCropTypes(type);
    }

    public List<CropTypes> getCropsWhereNameIsLike(String partOfName, SubscriptionType type) {
        return databaseMap.getCropsWhereNameIsLike(partOfName, type);
    }

    public List<CropTypes> getCropByLocation(double longitude, double latitude, SubscriptionType type) {
        return databaseMap.getBestCropOfLocation(longitude, latitude ,type);
    }

    public int addAddress(String street, int number, String dome) {
        return databaseUser.addAddress(street, number, dome);
    }

    public Boolean updateUser(String firstname, String lastname, String email, LocalDate newDate, int id) {
        return databaseUser.updateUser(firstname, lastname, email, newDate, id);
    }

    public void updateAddress(String street, int number, String dome, int id) {
        databaseUser.updateAddress(street, number, dome, id);
    }

    public Boolean updateSubscription(int userId, int subscriptionId) {
        return databaseUser.updateSubscription(userId, subscriptionId);
    }

    public List<Crop> getCropNames() {
        List<Crop> crops = new LinkedList<>();
        getCropTypes(SubscriptionType.PREMIUM).forEach(cropType -> {
            if (!cropsAlreadyInList(crops, cropType)) {
                crops.add(new Crop(cropType.getCropName(), cropType.getCropType()));
            }
        });
        return crops;
    }

    private boolean cropsAlreadyInList(List<Crop> crops, CropTypes cropType) {
        for (Crop crop: crops){
            if (crop.getName().equals(cropType.getCropName())){
                return true;
            }
        }
        return false;
    }
}
