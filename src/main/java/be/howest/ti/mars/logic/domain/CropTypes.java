package be.howest.ti.mars.logic.domain;

public class CropTypes {
    private final int longitude;
    private final int latitude;
    private final String cropName;
    private final String cropType;

    public CropTypes(int longitude, int latitude, String cropName, String cropType) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.cropName = cropName;
        this.cropType = cropType;

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
