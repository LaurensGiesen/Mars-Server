package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.Plant;
import be.howest.ti.mars.logic.domain.Product;
import be.howest.ti.mars.logic.domain.Seed;
import be.howest.ti.mars.logic.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DatabaseProductRepository {

    private static final DatabaseUsersRepository databaseUser = new DatabaseUsersRepository();

    public static Product ResultSetToProduct(ResultSet rs, boolean seed) throws SQLException {
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
