package be.howest.ti.mars.logic.domain;

import be.howest.ti.mars.logic.exceptions.UsersException;

import java.io.Serializable;

public class Subscription implements Serializable {
    private SubscriptionType type;
    private double price;
    private String description;

    public Subscription(SubscriptionType type) {
        this.type = type;
        this.price = takePriceFromType(type);
        this.description = takeDescriptionFromType(type);
    }

    private double takePriceFromType(SubscriptionType type) {
        if (type.equals(SubscriptionType.FREE)) {
            return 0.0; //free
        } else if (type.equals(SubscriptionType.BASIC)) {
            return 10.0; //must be changed
        }else if(type.equals(SubscriptionType.BASIC)){
            return 20.0; //must be changed
        }
        throw new UsersException("Invalid Subscription Type");
    }

    private String takeDescriptionFromType(SubscriptionType type) {
        if (type.equals(SubscriptionType.FREE)) {
            return "Free to use"; //must be finished to describe functionality on this type
        } else if (type.equals(SubscriptionType.BASIC)) {
            return "Basic version"; //must be finished to describe functionality on this type
        }else if( type.equals(SubscriptionType.PREMIUM) ){
            return "Premium version"; //must be finished to describe functionality on this type
        }
        throw new UsersException("Invalid Subscription Type");
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
