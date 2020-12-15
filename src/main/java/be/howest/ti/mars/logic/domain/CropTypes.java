package be.howest.ti.mars.logic.domain;

import java.util.Objects;

public class CropTypes {
    private final int longitude;
    private final int latitude;
    private final String cropName;
    private final String cropType;

    @Override
    public String toString() {
        return "CropTypes{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", cropName='" + cropName + '\'' +
                ", cropType='" + cropType + '\'' +
                '}';
    }

    public CropTypes(int longitude, int latitude, String cropName, String cropType) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.cropName = cropName;
        this.cropType = cropType;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CropTypes cropTypes = (CropTypes) o;
        return longitude == cropTypes.longitude && latitude == cropTypes.latitude && Objects.equals(cropName, cropTypes.cropName) && Objects.equals(cropType, cropTypes.cropType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude, cropName, cropType);
    }

    public int getLongitude() {
        return longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public String getCropName() {
        return cropName;
    }

    public String getCropType() {
        return cropType;
    }
}
