package be.howest.ti.mars.logic.domain;

import be.howest.ti.mars.logic.exceptions.UserException;

import java.io.Serializable;

public class Subscription implements Serializable {
    private final SubscriptionType type;
    private final double price;
    private final String description;

    public Subscription(SubscriptionType type, int price) {
        this.type = type;
        this.price = price;
        this.description = takeDescriptionFromType(type);
    }

    private String takeDescriptionFromType(SubscriptionType type) {
        if (type.equals(SubscriptionType.FREE)) {
            return "Free to use"; //must be finished to describe functionality on this type
        } else if (type.equals(SubscriptionType.BASIC)) {
            return "Basic version"; //must be finished to describe functionality on this type
        }else if( type.equals(SubscriptionType.PREMIUM) ){
            return "Premium version"; //must be finished to describe functionality on this type
        }
        throw new UserException("Invalid Subscription Type");
    }

    public SubscriptionType getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

}
