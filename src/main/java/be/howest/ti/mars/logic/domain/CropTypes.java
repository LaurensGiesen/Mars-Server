package be.howest.ti.mars.logic.domain;

public class CropTypes {
    private final int longitude;
    private final int latitude;
    private final int cropID;
    private final String cropType;

    public CropTypes(int longitude, int latitude, int cropID, String cropType) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.cropID = cropID;
        this.cropType = cropType;

    }

    public int getLongitude() {
        return longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getCropID() {
        return cropID;
    }

    public String getCropType() {
        return cropType;
    }
}
