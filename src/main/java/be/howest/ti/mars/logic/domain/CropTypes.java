package be.howest.ti.mars.logic.domain;

public class CropTypes {
    private final int longitude;
    private final int latitude;
    private final int cropID;

    public CropTypes(int longitude, int latitude, int cropID) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.cropID = cropID;
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
}
