package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.*;
import be.howest.ti.mars.logic.exceptions.ProductException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProductRepository{

    private static final DatabaseUsersRepository databaseUser = new DatabaseUsersRepository();
    private static final Logger LOGGER = Logger.getLogger(DatabaseProductRepository.class.getName());

    private static final String SQL_SELECT_ALL_PLANT = "select * from plants";
    private static final String SQL_ADD_PLANT = "insert into plants(name, price, owner_id, date, amount, image) values(?,?,?,?,?,?)";
    private static final String SQL_FIND_PLANT = "select * from plants where lower(name) like lower(?)";

    private static final String SQL_ADD_SEED = "insert into seeds(name, price, owner_id, date, amount, image) values(?,?,?,?,?,?)";
    private static final String SQL_SELECT_ALL_SEEDS = "select * from seeds";
    private static final String SQL_SELECT_ALL_SEEDS_WHERE_TYPE_IS_FRUIT = "select * from seeds where type='fruit'";
    private static final String SQL_SELECT_ALL_SEEDS_WHERE_TYPE_IS_VEGETABLE = "select * from seeds where type='vegetable'";
    private static final String SQL_FIND_SEED = "select * from seeds where lower(name) like lower(?)";

    private static final String SQL_FIND_SEED_BY_ID = "select * from seeds where id = ?";
    private static final String SQL_FIND_PLANTS_BY_ID = "select * from plants where id = ?";

    private static final String SQL_REMOVE_SEED = "delete from seeds where id=?";
    private static final String SQL_REMOVE_PLANT = "delete from plants where id=?";

    public int add(String name, Double price, User owner, LocalDate date1, int amount, String image, ProductType type) {
        if (type == ProductType.PLANT){
            return addProduct(name,price,owner,date1,amount,image, SQL_ADD_PLANT);
        } else if(type == ProductType.SEED){
            return addProduct(name,price,owner,date1,amount,image, SQL_ADD_SEED);
        }else{
            throw new ProductException();
        }
    }

    public List<Product> find(String search, ProductType type){
        if (type == ProductType.PLANT){
            return findProduct(search, SQL_FIND_PLANT,type);
        } else if(type == ProductType.SEED){
            return findProduct(search, SQL_FIND_SEED,type);
        }else {
            throw new ProductException();
        }
    }

    public List<Product> getAll(ProductType type) {
        if (type == ProductType.PLANT){
            return getAllByQuery(SQL_SELECT_ALL_PLANT,type);
        } else if (type == ProductType.SEED) {
            return getAllByQuery(SQL_SELECT_ALL_SEEDS,type);
        }else{
            throw new ProductException();
        }
    }

    public List<Product> getAllSeedsWhereTypeIsFruit() {
        List<Product> products = new LinkedList<>();
        getAllByQuery(SQL_SELECT_ALL_SEEDS_WHERE_TYPE_IS_FRUIT, ProductType.SEED);
        return products;
    }

    public List<Product> getAllSeedsWhereTypeIsVegetable() {
        List<Product> products = new LinkedList<>();
        getAllByQuery(SQL_SELECT_ALL_SEEDS_WHERE_TYPE_IS_VEGETABLE, ProductType.SEED);
        return products;

    }

    private int addProduct(String name, Double price, User owner, LocalDate date1, int amount, String image, String query) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, owner.getId());
            stmt.setDate(4, Date.valueOf(date1));
            stmt.setInt(5, amount);
            stmt.setString(6, "image");
            stmt.executeUpdate();
            try (ResultSet autoId = stmt.getGeneratedKeys()) {
                autoId.next();
                return autoId.getInt(1);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new ProductException("Unable to add plants");
        }
    }

    public List<Product> findProduct(String search, String query, ProductType type) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)
        ) {
            stmt.setString(1, search);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Product> productsByName = new ArrayList<>();
                while (rs.next()) {
                    productsByName.add(resultSetToProduct(rs,type));
                }
                return productsByName;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new ProductException("Unable to find products");
        }
    }

    public List<Product> getAllByQuery(String query, ProductType type){
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                products.add(resultSetToProduct(rs,type));
            }
            return products;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new ProductException("Unable to get all Seeds");
        }
    }

    public Product resultSetToProduct(ResultSet rs, ProductType type) throws SQLException {
        if(type == ProductType.PLANT){
            return resultSetToPlant(rs);
        }else if (type == ProductType.SEED){
            return resultSetToSeed(rs);
        }else{
            throw new ProductException("Invalid ProductType");
        }
    }

    private Product resultSetToPlant(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        double price = rs.getDouble("price");
        LocalDate date = rs.getDate("date").toLocalDate();
        int amount = rs.getInt("amount");
        String image = getImage(id);
        int ownerId = rs.getInt("owner_id");
        User owner = databaseUser.getById(ownerId);
        return new Product(id, name, price, owner, date, amount, image, ProductType.PLANT);
    }

    private Product resultSetToSeed(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        double price = rs.getDouble("price");
        return new Product(id, name, price, ProductType.SEED);
    }

    private String getImage(int id) {
        try{
            byte[] fileContent = FileUtils.readFileToByteArray(new File("images/" + id + ".png"));
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(fileContent);
        }catch (IOException ex){
            LOGGER.log(Level.WARNING, "Failed To Get Image");
            return null;
        }
    }

    public Product getById(int productId, String productType) {
        if (productType.equalsIgnoreCase(ProductType.PLANT.name())){
            return findProduct(String.valueOf(productId), SQL_FIND_PLANTS_BY_ID,ProductType.PLANT).get(0);
        } else if (productType.equalsIgnoreCase(ProductType.SEED.name())) {
            return findProduct(String.valueOf(productId), SQL_FIND_SEED_BY_ID,ProductType.SEED).get(0);
        }else{
            throw new ProductException();
        }
    }

    public Boolean removeProduct(Product product) {
        if (product.getType() == ProductType.PLANT) {
            return remove(product.getProductId(), SQL_REMOVE_PLANT);
        }else if(product.getType() == ProductType.SEED) {
            return remove(product.getProductId(), SQL_REMOVE_SEED);
        }
        throw new ProductException("Invalid Product type");
    }

    public Boolean remove(int userId, String query){
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)
        ) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new ProductException("Unable to find products");
        }
    }
}
