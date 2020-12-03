package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.Plant;
import be.howest.ti.mars.logic.domain.Product;

import java.util.List;

public interface DatabaseInterface {

    void add(Object obj);
    List<Object> getAll();
    List<Object> find(String name);


}
