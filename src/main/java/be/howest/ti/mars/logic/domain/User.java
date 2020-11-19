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
    private final Map<Product,Integer> harvested;
    private final Map<Product,Integer> history;
    private final Map<Plant, Integer> planted;
    private Location location;

    public User(int id, String firstName, String lastName, String email, Date dateOfBirth, Subscription subscription, Location location, Map<Product, Integer> harvested, Map<Product, Integer> history, Map<Plant, Integer> planted) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.location = location;
        this.dateOfBirth = dateOfBirth;
        this.subscription = subscription;
        this.harvested = harvested;
        this.history = history;
        this.planted = planted;
    }

    public User(int id, String firstName, String lastName, String email, Date dateOfBirth, Subscription subscription, Location location) {
        this(id, firstName, lastName, email, dateOfBirth, subscription,location, new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    public User(String firstName, String lastName, String email, Date dateOfBirth, Subscription subscription, Location location) {
        this(-1,firstName, lastName, email, dateOfBirth, subscription, location, new HashMap<>(), new HashMap<>(), new HashMap<>());
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

    public Location getLocation() {
        return location;
    }

    public void setEmail(String email) {
       this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void changeSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public void setLocation(Location location) {
        this.location = location;
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
