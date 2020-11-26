package be.howest.ti.mars.logic.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionTest {

    Subscription free = new Subscription(SubscriptionType.FREE);
    Subscription basic = new Subscription(SubscriptionType.BASIC);
    Subscription premium = new Subscription(SubscriptionType.PREMIUM);

    @Test
    void getType() {
        assertEquals(SubscriptionType.FREE, free.getType());
        assertEquals(SubscriptionType.BASIC, basic.getType());
        assertEquals(SubscriptionType.PREMIUM, premium.getType());
    }

    @Test
    void getPrice() {
        assertEquals(0, free.getPrice());
        assertEquals(10, basic.getPrice());
        assertEquals(20, premium.getPrice());
    }

    @Test
    void getDescription() {
        assertEquals("Free to use", free.getDescription());
        assertEquals("Basic version", basic.getDescription());
        assertEquals("Premium version", premium.getDescription());
    }
}