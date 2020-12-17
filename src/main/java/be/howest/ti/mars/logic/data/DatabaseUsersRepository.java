package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.*;
import be.howest.ti.mars.logic.exceptions.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUsersRepository {
    private static final Logger LOGGER = Logger.getLogger(DatabaseUsersRepository.class.getName());
    private static final String SQL_INSERT_ADDRESS = "insert into addresses(street, number, dome) VALUES(?,?,?)";
    private static final String SQL_INSERT_USER = "insert into users(firstname, lastname, email, date_of_birth, subscription_id, address_id) VALUES(?,?,?,?,?,?)";
    public static final String SQL_INSERT_FAVORITE = "insert into FAVORITES(user_id, product_id, product_type, amount) VALUES(?,?,?,?)";
    private static final String SQL_INSERT_BASKET = "insert into BASKETS(user_id, product_id, product_type,amount) VALUES(?,?,?,?)";
    private static final String SQL_SELECT_FAVORITE = "select * from favorites where user_id=?";
    private static final String SQL_SELECT_BASKET = "select * from baskets where user_id=?";
    private static final String SQL_REMOVE_FAVORITE = "delete from favorites where user_id=? and product_id=? and product_type=? and amount=?";
    private static final String SQL_REMOVE_BASKET = "delete from baskets where user_id=? and product_id=? and product_type=? and amount=?";

    private static final String SQL_UPDATE_USER = "update users set firstname = ?, lastname = ?, email = ?, date_of_birth = ? where userid = ?";
    private static final String SQL_UPDATE_ADDRESS = "update addresses set street = ?, number = ?, dome = ? where id = ?";
    private static final String SQL_UPDATE_SUBSCRIPTION = "update users set subscription_id = ? where userid = ?";

    private static final String SQL_SELECT_USER_BY_ID = "select * from users u join subscriptions s on s.id = u.subscription_id join addresses a on a.id = u.address_id where userId = ?";

    DatabaseProductRepository usersRepository = new DatabaseProductRepository();

    public int add(String firstname, String lastname, String email, LocalDate newDate, Subscription subscription, int addressId) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_INSERT_USER,
                     Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, email);
            stmt.setDate(4, java.sql.Date.valueOf(newDate));
            stmt.setInt(5, subscription.getType().getValue());
            stmt.setInt(6, addressId);
            stmt.executeUpdate();

            try (ResultSet autoId = stmt.getGeneratedKeys()) {
                autoId.next();
                return autoId.getInt(1);
            }

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new UsersException("Failed to add a user");
        }
    }

    public User getById(int userId) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            stmt.setInt(1, userId);
            User user;
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                user = resultSetToUser(rs);
            }
            return user;
        } catch (SQLException ex) {
            LOGGER.log(Level.WARNING, "Failed To Get User");
            throw new ProductException("Failed To Get User", ex);
        }
    }

    private User resultSetToUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("userId");
        String firstname = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String email = rs.getString("email");
        LocalDate date = rs.getDate("date_of_birth").toLocalDate();
        String name = rs.getString("name");
        String street = rs.getString("street");
        int number = rs.getInt("number");
        String dome = rs.getString("dome");
        Address address = new Address(street, number, dome);
        SubscriptionType type = getSubscriptionType(name);
        Subscription subscription = new Subscription(type);
        return new User(id, firstname, lastName, email, date, subscription, address);
    }

    private SubscriptionType getSubscriptionType(String name) {
        if (SubscriptionType.FREE.name().equalsIgnoreCase(name)) {
            return SubscriptionType.FREE;
        } else if (SubscriptionType.BASIC.name().equalsIgnoreCase(name)) {
            return SubscriptionType.BASIC;
        } else if (SubscriptionType.PREMIUM.name().equalsIgnoreCase(name)) {
            return SubscriptionType.PREMIUM;
        }
        return null;
    }

    public void addToFavorite(int id, List<Product> products, int amount) {
        products.forEach(product -> updateProductOfUser(id, product,amount, SQL_INSERT_FAVORITE));
    }

    public Boolean updateProductOfUser(int userId, Product product, int amount, String query) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, product.getProductId());
            stmt.setString(3, product.getType().name().toLowerCase());
            stmt.setInt(4, amount);
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            LOGGER.log(Level.WARNING, "Failed To Add Product");
            throw new ProductException("Failed To Add Product", ex);
        }
    }

    public List<Product> getFavorites(int userId) {
        return getProductsByUserId(userId, SQL_SELECT_FAVORITE);
    }

    private Product resultSetToFavorites(ResultSet rs) throws SQLException {
        int productId = rs.getInt("product_id");
        String productType = rs.getString("product_type");
        return usersRepository.getById(productId, productType);
    }

    public Boolean addToBasket(int userId, Product product, int amount) {
        return updateProductOfUser(userId, product,amount , SQL_INSERT_BASKET);
    }

    public List<Product> getBasket(int id) {
        return getProductsByUserId(id, SQL_SELECT_BASKET);
    }

    private List<Product> getProductsByUserId(int userId, String query) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)
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

    public Boolean removeProductFromFavorite(int userId, Product product, int amount) {
        return updateProductOfUser(userId, product, amount ,SQL_REMOVE_FAVORITE);

    }

    public Boolean removeProductFromBasket(int userId, Product product, int amount ) {
        return updateProductOfUser(userId, product,amount, SQL_REMOVE_BASKET);
    }

    public int addAddress(String street, int number, String dome) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_INSERT_ADDRESS,
                     Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, street);
            stmt.setInt(2, number);
            stmt.setString(3, dome);
            stmt.executeUpdate();
            try (ResultSet autoId = stmt.getGeneratedKeys()) {
                autoId.next();
                return autoId.getInt(1);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new UsersException("Failed to add a address");
        }
    }

    public Boolean updateUser(String firstname, String lastname, String email, LocalDate newDate, int id) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_USER)
        ) {
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, email);
            stmt.setDate(4, java.sql.Date.valueOf(newDate));
            stmt.setInt(5, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new UsersException("Failed to update user info");
        }
    }

    public void updateAddress(String street, int number, String dome, int id) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_ADDRESS)
        ) {
            stmt.setString(1, street);
            stmt.setInt(2, number);
            stmt.setString(3, dome);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new UsersException("Failed to update address info");
        }
    }

    public Boolean updateSubscription(int userId, int subscriptionId) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_SUBSCRIPTION)
        ) {
            stmt.setInt(1, subscriptionId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new UsersException("Failed to update address info");
        }
    }
}
