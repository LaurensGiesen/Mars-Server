package be.howest.ti.mars.logic.domain;
import java.time.LocalDate;
import java.util.Objects;

public class User {
    private final String firstName;
    private final String lastName;
    private String email;
    private int id;
    private final LocalDate dateOfBirth;
    private Subscription subscription;
    private final ProductCollection harvest;
    private final ProductCollection history;
    private final Basket basket;
    private final Address address;


    public User(int id, String firstName, String lastName, String email, LocalDate dateOfBirth, Subscription subscription, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.subscription = subscription;
        this.address = address;
        this.harvest = new Harvest();
        this.history = new History();
        this.basket = new Basket();
    }

    public User(String firstName, String lastName, String email, LocalDate dateOfBirth, Subscription subscription, Address address) {
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Basket getBasket() {
        return basket;
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
                ", harvested=" + harvest +
                ", history=" + history +
                ", basket=" + basket +
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