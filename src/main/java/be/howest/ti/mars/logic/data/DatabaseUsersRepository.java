package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.User;
import be.howest.ti.mars.logic.exceptions.*;

import java.sql.*;
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
            stmt.setInt(1, user.getId());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getEmail());
            stmt.setDate(5, java.sql.Date.valueOf(user.getDateOfBirth()));
            stmt.setInt(6, user.getSubscription().getType().getValue());
            stmt.setInt(7, user.getFavorites().getId());
            stmt.setInt(8, user.getBasket().getId());
            stmt.setInt(9, user.getAddress().getId());
            stmt.executeUpdate();
            //return true;

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

}
