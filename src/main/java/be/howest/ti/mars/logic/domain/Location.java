package be.howest.ti.mars.logic.domain;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Location {
    private BigDecimal longitude;
    private BigDecimal latitude;
    private int locationId;
    private List<Product> advisedPlants;
    private double temperature;
    private int dli; // daily light integral -> value must be between 20 and 30
    private String humidity; // must be a percentage
    private String soilType;
    private double phValue; // acidity of the water
    private double taw; // total available water
    private String npk; // value of nitrogen, phosphorus and potassium -> example: 10-10-9

    public Location(BigDecimal longitude, BigDecimal latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.advisedPlants = new LinkedList<>();
        this.temperature = -1;
        this.dli = -1;
        this.humidity = null;
        this.soilType = null;
        this.phValue = -1;
        this.taw = -1;
        this.npk = null;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setAdvisedPlants(List<Product> advisedPlants) {
        this.advisedPlants = advisedPlants;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setDli(int dli) {
        this.dli = dli;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public void setPhValue(double phValue) {
        this.phValue = phValue;
    }

    public void setTaw(double taw) {
        this.taw = taw;
    }

    public void setNpk(String npk) {
        this.npk = npk;
    }

    @Override
    public String toString() {
        return "Location{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", advisedPlants=" + advisedPlants +
                ", temperature=" + temperature +
                ", DLI=" + dli +
                ", humidity='" + humidity + '\'' +
                ", soilType='" + soilType + '\'' +
                ", PHValue=" + phValue +
                ", TAW=" + taw +
                ", NPK='" + npk + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return locationId == location.locationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId);
    }
}
