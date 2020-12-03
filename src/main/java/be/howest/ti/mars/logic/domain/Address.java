package be.howest.ti.mars.logic.domain;

public class Address {
    private final int id;
    private final String street;
    private final int number;
    private final String dome;

    public Address(int id, String street, int number, String dome) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.dome = dome;
    }

    public Address(String street, int number, String dome) {
        this(-1, street,number,dome);
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

    public int getId() {
        return id;
    }
}
