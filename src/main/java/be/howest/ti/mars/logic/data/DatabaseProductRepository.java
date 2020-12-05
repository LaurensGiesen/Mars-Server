package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.*;
import be.howest.ti.mars.logic.exceptions.PlantException;
import be.howest.ti.mars.logic.exceptions.ProductException;
import be.howest.ti.mars.logic.exceptions.SeedException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProductRepository{

    private static final DatabaseUsersRepository databaseUser = new DatabaseUsersRepository();
    private static final Logger LOGGER = Logger.getLogger(DatabaseProductRepository.class.getName());

    private static final String SQL_SELECT_ALL_PLANT = "select * from plants";
    private static final String SQL_ADD_PLANT = "insert into plants(id, name, price, owner_id, date, amount, image) values(?,?,?,?,?,?,?)";
    private static final String SQL_FIND_PLANT = "select * from plants where name like (?)";

    private static final String SQL_ADD_SEED = "insert into seeds(id, name, price, owner_id, date, amount, image) values(?,?,?,?,?,?,?)";
    private static final String SQL_SELECT_ALL_SEEDS = "select * from seeds";
    private static final String SQL_SELECT_ALL_SEEDS_WHERE_TYPE_IS_FRUIT = "select * from seeds where type='fruit'";
    private static final String SQL_SELECT_ALL_SEEDS_WHERE_TYPE_IS_VEGETABLE = "select * from seeds where type='vegetable'";
    private static final String SQL_FIND_SEED = "select * from seeds where name like (?)";


    public void add(Product product, ProductType type) {
        if (type == ProductType.PLANT){
            addProduct(product, SQL_ADD_PLANT);
        } else if(type == ProductType.SEED){
            addProduct(product, SQL_ADD_SEED);
        }else{
            throw new ProductException();
        }
    }

    public List<Product> find(String search, ProductType type){
        if (type == ProductType.PLANT){
            return findProduct(search, SQL_FIND_PLANT);
        } else if(type == ProductType.SEED){
            return findProduct(search, SQL_FIND_SEED);
        }else {
            throw new ProductException();
        }
    }

    public List<Product> getAll(ProductType type) {
        if (type == ProductType.PLANT){
            return getByQuery(SQL_SELECT_ALL_PLANT);
        } else if (type == ProductType.SEED) {
            return getByQuery(SQL_SELECT_ALL_SEEDS);
        }else{
            throw new ProductException();
        }
    }

    public List<Seed> getAllSeedsWhereTypeIsFruit() {
        List<Seed> seeds = new LinkedList<>();
        getByQuery(SQL_SELECT_ALL_SEEDS_WHERE_TYPE_IS_FRUIT).forEach(seed -> seeds.add((Seed) seed));
        return seeds;
    }

    public List<Seed> getAllSeedsWhereTypeIsVegetable() {
        List<Seed> seeds = new LinkedList<>();
        getByQuery(SQL_SELECT_ALL_SEEDS_WHERE_TYPE_IS_VEGETABLE).forEach(seed -> seeds.add((Seed) seed));
        return seeds;

    }

    private void addProduct(Product product, String query) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, product.getProductId());
            stmt.setString(2, product.getName());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getOwner().getId());
            stmt.setDate(5, Date.valueOf(product.getDate()));
            stmt.setInt(6, product.getAmount());
            stmt.setString(7, product.getImage());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new PlantException("Unable to add plants");
        }
    }

    public List<Product> findProduct(String search, String query) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)

        ) { stmt.setString(1, search);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Product> productsByName = new ArrayList<>();
                while (rs.next()) {
                    productsByName.add(DatabaseProductRepository.resultSetToProduct(rs,false));
                }
                return productsByName;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new PlantException("Unable to find products");
        }
    }

    public List<Product> getByQuery(String query){
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            List<Product> seeds = new ArrayList<>();
            while (rs.next()) {
                seeds.add(DatabaseProductRepository.resultSetToProduct(rs,true));
            }
            return seeds;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new SeedException("Unable to get all Seeds");
        }
    }

    public static Product resultSetToProduct(ResultSet rs, boolean seed) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        double price = rs.getDouble("price");
        LocalDate date = rs.getDate("date").toLocalDate();
        int amount = rs.getInt("amount");
        String image = rs.getString("image");
        int ownerId = rs.getInt("owner_id");
        User owner = databaseUser.getById(ownerId);
        if (seed){
            return new Seed(id, name, price, owner, date, amount, image);
        }
        return new Plant(id, name, price, owner, date, amount, image);
    }
}
