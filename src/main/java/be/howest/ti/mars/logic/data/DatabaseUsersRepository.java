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
    private static final String SQL_INSERT_USER = "insert into users(firstname, lastname, email, date_of_birth, subscription_Type) VALUES(?,?,?,?,?)";


    public void add(Object user) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_INSERT_USER);

        ) {
            User user1 = (User) user;
            stmt.setString(1, user1.getFirstName());
            stmt.setString(2, user1.getLastName());
            stmt.setString(3, user1.getEmail());
            stmt.setDate(4, java.sql.Date.valueOf(user1.getDateOfBirth()));
            stmt.setString(5, user1.getSubscription().getDescription());
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
