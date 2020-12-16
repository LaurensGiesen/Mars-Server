package be.howest.ti.mars.logic.domain;

import java.util.Objects;

public class CropTypes {
    private final double longitude;
    private final double latitude;
    private final String cropName;
    private final String cropType;
    private final int ratio;

    @Override
    public String toString() {
        return "CropTypes{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", cropName='" + cropName + '\'' +
                ", cropType='" + cropType + '\'' +
                ", ratio='" + ratio + '\'' +
                '}';
    }

    public CropTypes(double longitude, double latitude, String cropName, String cropType, int ratio) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.cropName = cropName;
        this.cropType = cropType;
        this.ratio = ratio;
    }

    public int getRatio() {
        return ratio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CropTypes cropTypes = (CropTypes) o;
        return longitude == cropTypes.longitude && latitude == cropTypes.latitude && Objects.equals(cropName, cropTypes.cropName) && Objects.equals(cropType, cropTypes.cropType) && Objects.equals(ratio, cropTypes.ratio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude, cropName, cropType,ratio);
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getCropName() {
        return cropName;
    }

    public String getCropType() {
        return cropType;
    }
}
