package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.*;
import be.howest.ti.mars.logic.exceptions.ProductException;
import org.h2.tools.Server;

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

    public void createUser(User user){
        databaseUser.add(user);
    }



    public User getUserById(int ownerId) {
        return databaseUser.getById(ownerId);
    }

    public Product getSeedByName(String crop1) {
        if (databaseProduct.find(crop1, ProductType.SEED) == null){
            return null;
        }
        return databaseProduct.find(crop1,ProductType.PLANT).get(0);
    }

    public List<Product> getProduct(ProductType type) {
        return databaseProduct.getAll(type);
    }

    public void createProduct(String name, Double price, User owner, LocalDate date1, int amount, String image, ProductType type) {
        databaseProduct.add(name,price,owner,date1,amount,image,type);
    }
}
