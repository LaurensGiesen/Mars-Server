package be.howest.ti.mars.logic.domain;

public class Subscription {
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
        }
        return 20.0; //must be changed
    }

    private String takeDescriptionFromType(SubscriptionType type) {
        if (type.equals(SubscriptionType.FREE)) {
            return "Free to use"; //must be finished to describe functionality on this type
        } else if (type.equals(SubscriptionType.BASIC)) {
            return "Basic version"; //must be finished to describe functionality on this type
        }
        return "Premium version"; //must be finished to describe functionality on this type
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

    public void changeSubscription(SubscriptionType type) {
        this.type = type;
        this.price = takePriceFromType(type);
        this.description = takeDescriptionFromType(type);
    }
}
