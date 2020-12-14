package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.*;
import org.h2.tools.Server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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

    private static final MarsRepository INSTANCE = new MarsRepository();
    private Server dbWebConsole;
    private String username;
    private String password;
    private String url;


    private MarsRepository() { }

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
        if (seeds == null || seeds.isEmpty()){
            return null;
        }
        return databaseProduct.find(crop1,ProductType.SEED).get(0);
    }

    public List<Product> getProduct(ProductType type) {
        return databaseProduct.getAll(type);
    }

    public void createProduct(String name, Double price, User owner, LocalDate date1, int amount, String image, ProductType type) {
        int id = databaseProduct.add(name,price,owner,date1,amount,image,type);
        createImage(image, id);
    }

    private void createImage(String image, int id) {
        String base64Image = image.split(",")[1];
        String extension = image.split("/")[1].split(";")[0];
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
        try (OutputStream stream = new FileOutputStream("images/" + id + "." + extension)) {
            stream.write(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int createUser(String firstname, String lastname, String email, LocalDate newDate, Subscription subscription, Address address) {
        return databaseUser.add(firstname, lastname, email, newDate, subscription, address);
    }

    public void addFavoriteToUser(int id, List<Product> products) {
        databaseUser.addToFavorite(id, products);
    }

    public boolean addProductToFavorite(int userId ,int productId,String productType) {
        Product product = databaseProduct.getById(productId, productType);
        return databaseUser.addProductTo(userId, product, DatabaseUsersRepository.SQL_SELECT_FAVORITE);
    }

    public List<Product> getFavorites(int userId) {
        return databaseUser.getFavorites(userId);
    }

    public Boolean addProductToBasket(int userId, int productId, String productType) {
        Product product = databaseProduct.getById(productId, productType);
        return databaseUser.addToBasket(userId, product);
    }
}
