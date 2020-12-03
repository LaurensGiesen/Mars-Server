package be.howest.ti.mars.logic.domain;

import java.sql.Date;
import java.time.LocalDate;

public class Plant extends Product{


    public Plant(int productId, String name, double price, User owner, LocalDate date, int amount, String image) {
        super(productId,name,price, owner, date, amount, image);
    }

    @Override
    public String toString() {
        return super.getName();
    }
}
