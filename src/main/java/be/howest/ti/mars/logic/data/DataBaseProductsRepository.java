package be.howest.ti.mars.logic.data;
import be.howest.ti.mars.logic.domain.Plant;
import be.howest.ti.mars.logic.domain.Product;
import be.howest.ti.mars.logic.exceptions.PlantException;
import be.howest.ti.mars.logic.exceptions.SeedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseProductsRepository implements ProductsRepository {
    private static final Logger LOGGER = Logger.getLogger(DataBaseProductsRepository.class.getName());
    private static final String SQL_SELECT_ALL_PRODUCTS = "select * from products";
    private static final String SQL_ADD_PRODUCT = "insert into products(name, price, amount, date, image) values(?,?,?,?,?)";
    private static final String SQL_FIND_PRODUCT = "select * from products where name like (?)";
    @Override
    public void addPlant(Plant plant) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_ADD_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {


            stmt.setString(1, plant.getName());
            stmt.setDouble(2, plant.getPrice());
            stmt.setInt(3, plant.getAmount());
            stmt.setDate(4, plant.getDate());
            stmt.setString(5, plant.getImage());

            stmt.executeUpdate();
            ResultSet rsKey = stmt.getGeneratedKeys();
            rsKey.next();

            plant.setProductId(rsKey.getInt(1));
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new PlantException("Unable to add plants");
        }
    }

    @Override
    public List<Product> getProducts() {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_PRODUCTS);
             ResultSet rs = stmt.executeQuery()) {
            List<Product> allProducts = new ArrayList<>();
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Date date = rs.getDate("date");
                int amount = rs.getInt("amount");
                String image = rs.getString("image");
                Product product = new Plant(productId, name, price, date,amount, image);
                allProducts.add(product);
            }
            return allProducts;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new SeedException("Unable to get all products");
        }
    }

    @Override
    public List<Product> findProduct(String ch) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_FIND_PRODUCT)

        ) { stmt.setString(1, ch);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Product> productsByname = new ArrayList<>();
                while (rs.next()) {
                    int productId = rs.getInt("product_id");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    int amount = rs.getInt("amount");
                    Date date = rs.getDate("date");
                    String image = rs.getString("image");
                    Product product = new Plant(productId, name, price,date,amount, image);
                    productsByname.add(product);
                }
                return productsByname;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new PlantException("Unable to find products");
        }
    }
}
