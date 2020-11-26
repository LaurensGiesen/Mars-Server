package be.howest.ti.mars.logic.data;
import be.howest.ti.mars.logic.domain.Plant;
import be.howest.ti.mars.logic.domain.Product;
import be.howest.ti.mars.logic.exceptions.SeedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseProductsRepository implements ProductsRepository {
    private static final Logger LOGGER = Logger.getLogger(DataBaseProductsRepository.class.getName());
    private static final String SQL_SELECT_ALL_PRODUCTS = "select * from products";
    @Override
    public void addProduct(Product product) {
    }

    @Override
    public List<Product> getProducts() {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_PRODUCTS);
             ResultSet rs = stmt.executeQuery()) {
            List<Product> allProducts = new ArrayList<>();
            while (rs.next()) {
                int product_id = rs.getInt("product_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Date date = rs.getDate("date");
                int amount = rs.getInt("amount");
                String image = rs.getString("image");
                Product product = new Plant(product_id, name, price, date,amount, image);
                allProducts.add(product);
            }
            return allProducts;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new SeedException("Unable to get all Seeds");
        }
    }

    @Override
    public List<Product> findProduct(String name) {
        return null;
    }
}
