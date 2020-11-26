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
    private final ProductCollection harvested;
    private final ProductCollection history;
    private final Basket basket;
    private final Favorite favorites;
    private final Address address;


    public User(int id, String firstName, String lastName, String email, Date dateOfBirth, Subscription subscription, Address address, Favorite favorites) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.subscription = subscription;
        this.favorites = favorites;
        this.address = address;
        this.harvested = new Harvest();
        this.history = new History();
        this.basket = new Basket();
    }

    public User(String firstName, String lastName, String email, Date dateOfBirth, Subscription subscription, Address address, Favorite favorites) {
        this(-1, firstName, lastName, email, dateOfBirth, subscription, address, favorites);
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

    public Subscription getSubscription() {
        return subscription;
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

    public Favorite getFavorites() {
        return favorites;
    }

    public ProductCollection getHarvested() {
        return harvested;
    }

    public ProductCollection getHistory() {
        return history;
    }

    public Address getAddress() {
        return address;
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
                //", totalPrice=" + totalPrice +
                //", planted=" + planted +
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