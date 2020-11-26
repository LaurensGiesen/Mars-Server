package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.Seed;
import be.howest.ti.mars.logic.exceptions.SeedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseSeedsRepository implements SeedsRepository {
    private static final Logger LOGGER = Logger.getLogger(DatabaseSeedsRepository.class.getName());
    private static final String SQL_SELECT_ALL_SEEDS = "select * from seeds";


    @Override
    public List<Seed> getAllSeeds() {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_SEEDS);
             ResultSet rs = stmt.executeQuery()) {
            List<Seed> seeds = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("seedid");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                double weight = rs.getDouble("weight");
                String type = rs.getString("type");
                Seed seed = new Seed(id, name, price,weight, type);
                seeds.add(seed);
            }
            return seeds;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new SeedException("Unable to get all Seeds");
        }
    }

    @Override
    public List<Seed> getAllFruits() {
        return null;
    }

    @Override
    public List<Seed> getAllVeggies() {
        return null;
    }

}
