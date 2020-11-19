package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.User;
import be.howest.ti.mars.logic.exceptions.*;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUsersRepository implements UsersRepository {
    private static final Logger LOGGER = Logger.getLogger(DatabaseUsersRepository.class.getName());
    private static final String SQL_INSERT_USER = "insert into users(firstname, lastname, email, date_of_birth, subscriptionid) VALUES(?,?,?,?,?)";

    @Override
    public void addUser(User user) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setDate(4, user.getDateOfBirth());
            stmt.setInt(5, user.getSubscriptionId());

            stmt.executeUpdate();
            ResultSet rsKey = stmt.getGeneratedKeys();
            rsKey.next();

            user.setId(rsKey.getInt(1));

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new UsersException("Unable to add a user");
        }

    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public List<User> findUser(User user) {
        return null;
    }

//    public static void main(String[] args) {
//        User user = new User(-1, "Timo", "vertonghen", "timo.vertonghen@gmail.com",new Date(2000, 12,20), 1);
//        DatabaseUsersRepository repo = new DatabaseUsersRepository();
//        repo.addUser(user);
//
//    }
}
