package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.CropTypes;
import be.howest.ti.mars.logic.exceptions.CropTypeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseMapRepository {

    private static final Logger LOGGER = Logger.getLogger(DatabaseMapRepository.class.getName());

    private static final String SQL_SELECT_ALL_CROP_TYPES = "select l.*, s.name, s.type from locations l join seeds s on l.crop_id = s.id";
    private static final String SQL_SELECT_CROPS_WHERE_NAME_IS_LIKE = "select l.*, s.name, s.type from locations l join seeds s on l.crop_id = s.id where s.name like ?";

    public static CropTypes resultSetToCropType(ResultSet rs) throws SQLException {
        int lng = rs.getInt("longitude");
        int lat = rs.getInt("latitude");
        int cropID = rs.getInt("crop_id");
        String cropType = rs.getString("type");

        return new CropTypes(lng, lat, cropID, cropType);
    }

    public static List<CropTypes> getCropsWhereNameIsLike(String partOfName) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_SELECT_CROPS_WHERE_NAME_IS_LIKE);
             ) {
            stmt.setString(1, "%" + partOfName + "%");
            ResultSet rs = stmt.executeQuery();
            List<CropTypes> cropTypes = new ArrayList<>();
            while (rs.next()) {
                cropTypes.add(DatabaseMapRepository.resultSetToCropType(rs));
            }
            return cropTypes;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new CropTypeException("Unable to get crop types");
        }
    }

    public List<CropTypes> getAllCropTypes() {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_CROP_TYPES);
             ResultSet rs = stmt.executeQuery()) {
            List<CropTypes> cropTypes = new ArrayList<>();
            while (rs.next()) {
                cropTypes.add(DatabaseMapRepository.resultSetToCropType(rs));
            }
            return cropTypes;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new CropTypeException("Unable to get crop types");
        }
    }
}
