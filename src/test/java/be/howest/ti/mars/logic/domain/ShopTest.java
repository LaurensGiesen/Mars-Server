package be.howest.ti.mars.logic.domain;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {
    Date u1Date = new Date(1980,10,8);
    Subscription u1Subscription = new Subscription(SubscriptionType.BASIC);
    Location u1Location = new Location("2.1654", "25.35468");
    User u1 = new User(1, "Jan", "Janssens", "jan.janssens@student.howest.be", u1Date, u1Subscription, u1Location);

    Date u2Date = new Date(1994,6,4);
    Subscription u2Subscription = new Subscription(SubscriptionType.PREMIUM);
    Location u2Location = new Location("46.16871", "84.2315");
    User u2 = new User(2, "Piet", "DeLange", "piet.delange@student.howest.be", u2Date, u2Subscription, u2Location);

    Shop shop = new Shop();

    Plant p1 = new Plant("Wortel", 1);

    @Test
    void buyAndSellPlant() {
        assertThrows(NullPointerException.class, () -> shop.buyAndSellPlant(p1, u1, u2, 1));
        //moet aangepast worden wanneer de User klasse volledig af is
    }

    @Test
    void getPlants() {
        assertEquals(Collections.emptyList(), shop.getPlants());
        //moet aangepast worden wanneer de User klasse volledig af is
    }

    @Test
    void getSeeds() {
        assertEquals(Collections.emptyList(), shop.getPlants());
        //moet aangepast worden wanneer de User klasse volledig af is
    }
}