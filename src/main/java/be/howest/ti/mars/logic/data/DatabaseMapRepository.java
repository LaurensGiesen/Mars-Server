package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.CropTypes;
import be.howest.ti.mars.logic.domain.SubscriptionType;
import be.howest.ti.mars.logic.exceptions.CropTypeException;
import be.howest.ti.mars.logic.exceptions.SubscriptionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseMapRepository {

    private static final Logger LOGGER = Logger.getLogger(DatabaseMapRepository.class.getName());

    private static final String SQL_SELECT_ALL_CROP_TYPES = "select l.*, s.name, s.type from locations l join seeds s on l.crop_id = s.id";
    private static final String SQL_SELECT_CROPS_WHERE_NAME_IS_LIKE = "select l.*, s.name, s.type from locations l join seeds s on l.crop_id = s.id where lower(s.name) like ?";
    private static final String SQL_SELECT_CROPS_BY_LONG_AND_LAT = "select l.*, s.name, s.type from locations l join seeds s on l.crop_id = s.id where l.longitude >= ? and l.longitude <= ? and l.latitude >= ? and l.latitude <= ?";

    private final DatabaseUsersRepository usersRepository = new DatabaseUsersRepository();

    public CropTypes resultSetToCropType(ResultSet rs) throws SQLException {
        double lng = rs.getDouble("longitude");
        double lat = rs.getDouble("latitude");
        String cropName = rs.getString("name");
        String cropType = rs.getString("type");
        int ratio = rs.getInt("ratio");
        return new CropTypes(lng, lat, cropName, cropType, ratio);
    }

    public List<CropTypes> getCropsWhereNameIsLike(String partOfName, SubscriptionType type) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_SELECT_CROPS_WHERE_NAME_IS_LIKE)
        ) {
            stmt.setString(1, "%" + partOfName.toLowerCase() + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                List<CropTypes> cropTypes = new ArrayList<>();
                while (rs.next()) {
                    cropTypes.add(resultSetToCropType(rs));
                }
                return getCropTypesForUsersSubscription(cropTypes, type);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new CropTypeException("Unable to get crop types");
        }
    }

    public List<CropTypes> getCropTypesForUsersSubscription(List<CropTypes> cropTypes, SubscriptionType type) {
        if(type == SubscriptionType.FREE){
            return filterCropTypes(cropTypes, 0,3);
        }else if(type == SubscriptionType.BASIC){
            return filterCropTypes(cropTypes, 3,7);
        }else if(type == SubscriptionType.PREMIUM){
            return cropTypes;
        }
        throw new SubscriptionException("Invalid Subscription");
    }

    private List<CropTypes> filterCropTypes(List<CropTypes> cropTypes, int min, int max) {
        List<CropTypes> newCropTypes = new LinkedList<>();
        cropTypes.forEach(cropType -> {
            if (cropType.getRatio() >= min && cropType.getRatio() <= max){
                newCropTypes.add(cropType);
            }
        });
        return newCropTypes;
    }

    public List<CropTypes> getAllCropTypes(SubscriptionType type) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_CROP_TYPES);
             ResultSet rs = stmt.executeQuery()) {
            List<CropTypes> cropTypes = new ArrayList<>();
            while (rs.next()) {
                cropTypes.add(resultSetToCropType(rs));
            }
            return getCropTypesForUsersSubscription(cropTypes, type);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new CropTypeException("Unable to get crop types");
        }
    }

    public List<CropTypes> getBestCropOfLocation(double longitude, double latitude, SubscriptionType type) {
        return getCropsByLocation(longitude, latitude, 1, type);
    }

    public List<CropTypes> getCropsByLocation(double longitude, double latitude, int radius, SubscriptionType type) {
        try (Connection con = MarsRepository.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_SELECT_CROPS_BY_LONG_AND_LAT)
        ) {
            stmt.setDouble(1, longitude - radius);
            stmt.setDouble(2, longitude + radius);
            stmt.setDouble(3, latitude - radius);
            stmt.setDouble(4, latitude + radius);
            List<CropTypes> cropTypes = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cropTypes.add(resultSetToCropType(rs));
                }
            }
            return getCropTypesForUsersSubscription(cropTypes, type);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new CropTypeException("Unable to get crop types");
        }
    }
}
