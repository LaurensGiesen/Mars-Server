package be.howest.ti.mars.logic.domain;

import java.time.LocalDate;

public class Seed extends Product{

    public Seed(int productId, String name, double price, User owner, LocalDate date, int amount, String image) {
        super(productId, name, price, owner, date, amount, image);
    }

    @Override
    public String toString() {
        return super.getName();
    }
}
