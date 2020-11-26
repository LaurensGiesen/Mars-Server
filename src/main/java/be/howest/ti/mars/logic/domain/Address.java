package be.howest.ti.mars.logic.domain;

public class Address {
    private final String street;
    private final int number;
    private final String dome;

    public Address(String street, int number, String dome) {
        this.street = street;
        this.number = number;
        this.dome = dome;
    }

    public String getStreet() {
        return street;
    }

    public int getNumber() {
        return number;
    }

    public String getDome() {
        return dome;
    }
}
