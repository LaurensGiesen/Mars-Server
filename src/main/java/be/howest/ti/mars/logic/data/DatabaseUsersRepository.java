package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.*;
import be.howest.ti.mars.logic.exceptions.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUsersRepository {
    private static final Logger LOGGER = Logger.getLogger(DatabaseUsersRepository.class.getName());
    private static final String SQL_INSERT_USER = "insert into users(firstname, lastname, email, date_of_birth, subscriptionID, address_id) VALUES(?,?,?,?,?,?)";
    private static final String SQL_INSERT_FAVORITE = "insert into favorites(firstname, lastname, email, date_of_birth, subscriptionID, address_id) VALUES(?,?,?,?,?,?)";


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

    public void addFavorite(int id, List<Product> products) {
        products.forEach(product -> addFavorite(id, product));
    }

    public void addFavorite(int id, Product product) {
        LOGGER.log(Level.WARNING,"NYI");
    }
}
