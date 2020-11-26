package be.howest.ti.mars.logic.domain;

public enum SubscriptionType {
    FREE (1),BASIC (2),PREMIUM (3);

    private int value;

    SubscriptionType(int i) {
        value = i;
    }
}


