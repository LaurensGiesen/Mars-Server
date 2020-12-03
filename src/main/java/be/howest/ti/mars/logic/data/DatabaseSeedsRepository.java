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

public class DatabaseSeedsRepository implements DatabaseInterface {
    private static final Logger LOGGER = Logger.getLogger(DatabaseSeedsRepository.class.getName());
    private static final String SQL_SELECT_ALL_SEEDS = "select * from seeds";
    private static final String SQL_SELECT_ALL_SEEDS_WHERE_TYPE_IS_FRUIT = "select * from seeds where type='fruit'";
    private static final String SQL_SELECT_ALL_SEEDS_WHERE_TYPE_IS_VEGETABLE = "select * from seeds where type='vegetable'";

    @Override
    public void add(Object obj) {

    }

    @Override
    public List<Object> getAll() {
        return getByQuery(SQL_SELECT_ALL_SEEDS);
    }

    @Override
    public List<Object> find(String name) {
        return null;
    }

    public List<Seed> getAllSeedsWhereTypeIsFruit() {
        return (List<Seed>)(Seed) getByQuery(SQL_SELECT_ALL_SEEDS_WHERE_TYPE_IS_FRUIT);
    }

    public List<Seed> getAllSeedsWhereTypeIsVegetable() {
        return (List<Seed>)(Seed) getByQuery(SQL_SELECT_ALL_SEEDS_WHERE_TYPE_IS_VEGETABLE);
    }

    public List<Object> getByQuery(String query){
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            List<Object> seeds = new ArrayList<>();
            while (rs.next()) {
                seeds.add(DatabaseProductRepository.ResultSetToProduct(rs,true));
            }
            return seeds;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new SeedException("Unable to get all Seeds");
        }
    }
}
