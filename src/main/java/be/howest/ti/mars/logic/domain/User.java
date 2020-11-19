package be.howest.ti.mars.logic.domain;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User {
    private final String firstName;
    private final String lastName;
    private String eMail;
    private int id;
    private final Date dateOfBirth;
    private int subscriptionId;
    private final Map<Product,Integer> harvest;
    private final Map<Product,Integer> history;

    public User(int id, String firstName, String lastName, String eMail, Date dateOfBirth, int subscriptionId, Map<Product, Integer> harvest, Map<Product, Integer> history) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.dateOfBirth = dateOfBirth;
        this.subscriptionId = subscriptionId;
        this.harvest = harvest;
        this.history = history;
    }

    public User(int id, String firstName, String lastName, String eMail, Date dateOfBirth, int subscriptionId) {
        this(id, firstName, lastName, eMail, dateOfBirth, subscriptionId, new HashMap<>(), new HashMap<>());
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return eMail;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public Map<Product, Integer> getHarvest() {
        return harvest;
    }

    public Map<Product, Integer> getHistory() {
        return history;
    }

    public void setEmail(String email) {
       this.eMail = email;
    }

    public void setSubscriptionId(int id) {
        this.subscriptionId = id;
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
