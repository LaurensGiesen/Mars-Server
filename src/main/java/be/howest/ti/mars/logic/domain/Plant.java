package be.howest.ti.mars.logic.domain;

import java.sql.Date;

public class Plant extends Product{
    private final String image;
    private final Date date;
    public Plant(int product_id, String name, double price, Date date, int amount,String image) {
        super(product_id,name,price,amount);
        this.image = image;
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return super.getName();
    }
}
