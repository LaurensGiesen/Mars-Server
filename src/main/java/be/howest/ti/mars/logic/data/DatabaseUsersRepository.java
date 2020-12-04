package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.*;
import be.howest.ti.mars.logic.exceptions.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUsersRepository implements DatabaseInterface {
    private static final Logger LOGGER = Logger.getLogger(DatabaseUsersRepository.class.getName());
    private static final String SQL_INSERT_USER = "insert into users(userid, firstname, lastname, email, date_of_birth, subscriptionID, favorite_id, basket_id, address_id) VALUES(?,?,?,?,?,?,?,?,?)";


    public void add(Object user) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_INSERT_USER);

        ) {

            User user1 = (User) user;
            stmt.setInt(1, 1);
            stmt.setString(2, user1.getFirstName());
            stmt.setString(3, user1.getLastName());
            stmt.setString(4, user1.getEmail());
            stmt.setDate(5, java.sql.Date.valueOf(user1.getDateOfBirth()));
            stmt.setInt(6, user1.getSubscription().getType().getValue());
            stmt.setInt(7, user1.getFavorites().getId());
            stmt.setInt(8, user1.getBasket().getId());
            stmt.setInt(9, user1.getAddress().getId());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new UsersException("Unable to add a user");
        }
    }

    @Override
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
}
