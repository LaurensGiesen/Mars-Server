package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.*;
import be.howest.ti.mars.logic.exceptions.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUsersRepository {
    private static final Logger LOGGER = Logger.getLogger(DatabaseUsersRepository.class.getName());
    private static final String SQL_INSERT_USER = "insert into users(firstname, lastname, email, date_of_birth, subscription_id, address_id) VALUES(?,?,?,?,?,?)";
    private static final String SQL_INSERT_FAVORITE = "insert into FAVORITES(user_id, product_id, product_type) VALUES(?,?,?)";
    private static final String SQL_INSERT_BASKET = "insert into BASKET(user_id, product_id, product_type) VALUES(?,?,?)";
    public static final String SQL_SELECT_FAVORITE = "select * from favorites where user_id=?";
    DatabaseProductRepository usersRepository = new DatabaseProductRepository();

    public int add(String firstname, String lastname, String email, LocalDate newDate, Subscription subscription, Address address) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_INSERT_USER,
                     Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, email);
            stmt.setDate(4, java.sql.Date.valueOf(newDate));
            stmt.setInt(5, subscription.getType().getValue());
            stmt.setInt(6, address.getId());
            stmt.executeUpdate();

            try (ResultSet autoId = stmt.getGeneratedKeys()) {
                autoId.next();
                return autoId.getInt(1);
            }

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new UsersException("Unable to add a user");
        }
    }

    public List<Object> getAll() {
        return Collections.emptyList();
    }

    public List<Object> find(String user) {
        return Collections.emptyList();
    }

    public User getById(int ownerId) {
        LOGGER.log(Level.WARNING, "NYI");
        LocalDate date = LocalDate.now();
        return new User(ownerId, "NYI","NYI","NYI@gmail.com",date ,new Subscription(SubscriptionType.BASIC),new Address("NYI",1337,"NYI"), new Favorite());
    }

    public void addToFavorite(int id, List<Product> products) {
        products.forEach(product -> addProductTo(id, product, SQL_INSERT_FAVORITE));
    }

    public Boolean addProductTo(int userId , Product product, String query) {
        try(Connection con = MarsRepository.getConnection();
            PreparedStatement stmt = con.prepareStatement(query) ){
            stmt.setInt(1, userId);
            stmt.setInt(2, product.getProductId());
            stmt.setString(3, product.getType().name().toLowerCase());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.WARNING,"Failed To Add Product");
            throw new ProductException("Failed To Add Product", ex);
        }
        return true;
    }

    public List<Product> getFavorites(int userId) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_SELECT_FAVORITE)
        ) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Product> productsByName = new ArrayList<>();
                while (rs.next()) {
                    productsByName.add(resultSetToFavorites(rs));
                }
                return productsByName;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new ProductException("Unable to find products");
        }
    }

    private Product resultSetToFavorites(ResultSet rs) throws SQLException {
        int productId = rs.getInt("product_id");
        String productType = rs.getString("product_type");
        return usersRepository.getById(productId, productType);
    }

    public Boolean addToBasket(int userId, Product product) {
        return addProductTo(userId, product, SQL_INSERT_BASKET);
    }
}
