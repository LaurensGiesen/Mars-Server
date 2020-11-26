package be.howest.ti.mars.logic.domain;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User {
    private final String firstName;
    private final String lastName;
    private String email;
    private int id;
    private final Date dateOfBirth;
    private Subscription subscription;
    private final Map<Product, Integer> harvested;
    private final Map<Product, Integer> history;
    private Basket basket;
    private double totalPrice;
    private final Map<Plant, Integer> planted;
    private Address address;


    public User(int id, String firstName, String lastName, String email, Date dateOfBirth, Subscription subscription, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.subscription = subscription;
        this.address = address;
        this.harvested = new HashMap<>();
        this.history = new HashMap<>();
        this.basket = new Basket();
        this.totalPrice = 0.0;
        this.planted = new HashMap<>();
    }

    public User(String firstName, String lastName, String email, Date dateOfBirth, Subscription subscription, Address address) {
        this(-1, firstName, lastName, email, dateOfBirth, subscription, address);
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Map<Product, Integer> getHarvested() {
        return harvested;
    }

    public Map<Product, Integer> getHistory() {
        return history;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public Map<Plant, Integer> getPlanted() {
        return planted;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Basket getBasket() {
        return basket;
    }

    public void changeSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", dateOfBirth=" + dateOfBirth +
                ", subscription=" + subscription +
                ", harvested=" + harvested +
                ", history=" + history +
                ", basket=" + basket +
                ", totalPrice=" + totalPrice +
                ", planted=" + planted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}