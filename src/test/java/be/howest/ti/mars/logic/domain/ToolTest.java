package be.howest.ti.mars.logic.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToolTest {

    Tool t1 = new Tool(5.49, "Trowel", "A trowel will easily be among the most used and important tools in your arsenal. It's essentially a handheld shovel that you'll use for moving soil, digging, removing weeds, mixing fertilizer and much more.");

    @Test
    void getRentPrice() {
        assertEquals(5.49, t1.getRentPrice());
    }

    @Test
    void getName() {
        assertEquals("Trowel", t1.getName());
    }

    @Test
    void getDescription() {
        assertEquals("A trowel will easily be among the most used and important tools in your arsenal. It's essentially a handheld shovel that you'll use for moving soil, digging, removing weeds, mixing fertilizer and much more.", t1.getDescription());
    }

    @Test
    void setRentPrice() {
        getRentPrice();

        t1.setRentPrice(6.99);
        assertEquals(6.99, t1.getRentPrice());
    }
}