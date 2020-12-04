package be.howest.ti.mars.logic.data;
import be.howest.ti.mars.logic.domain.Plant;
import be.howest.ti.mars.logic.domain.Seed;
import be.howest.ti.mars.logic.exceptions.PlantException;
import be.howest.ti.mars.logic.exceptions.SeedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBasePlantRepository implements DatabaseInterface {
    private static final Logger LOGGER = Logger.getLogger(DataBasePlantRepository.class.getName());

    private static final String SQL_SELECT_ALL_PLANT = "select * from plants";
    private static final String SQL_ADD_PLANT = "insert into plants(id, name, price, owner_id, date, amount, image) values(?,?,?,?,?,?,?)";
    private static final String SQL_FIND_PLANT = "select * from plants where name like (?)";

    public void add(Object plant) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_ADD_PLANT, Statement.RETURN_GENERATED_KEYS)) {
            Plant plant1 = (Plant) plant;
            stmt.setInt(1, plant1.getProductId());
            stmt.setString(2, plant1.getName());
            stmt.setDouble(3, plant1.getPrice());
            stmt.setInt(4, plant1.getOwner().getId());
            stmt.setDate(5, Date.valueOf(plant1.getDate()));
            stmt.setInt(6, plant1.getAmount());
            stmt.setString(7, plant1.getImage());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new PlantException("Unable to add plants");
        }
    }

    @Override
    public List<Object> getAll() {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_PLANT);
             ResultSet rs = stmt.executeQuery()) {
            List<Object> allProducts = new ArrayList<>();
            while (rs.next()) {
                allProducts.add(DatabaseProductRepository.ResultSetToProduct(rs,false));
            }
            return allProducts;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new SeedException("Unable to get all products");
        }
    }


    public List<Object> find(String ch) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_FIND_PLANT)

        ) { stmt.setString(1, ch);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Object> productsByName = new ArrayList<>();
                while (rs.next()) {
                    productsByName.add(DatabaseProductRepository.ResultSetToProduct(rs,false));
                }
                return productsByName;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new PlantException("Unable to find products");
        }
    }
}
