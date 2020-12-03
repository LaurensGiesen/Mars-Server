package be.howest.ti.mars.logic.data;

import java.util.List;

public interface DatabaseInterface {

    static final DatabaseUsersRepository databaseUser = new DatabaseUsersRepository();
    static final DataBasePlantRepository databaseProduct = new DataBasePlantRepository();
    static final DatabaseSeedsRepository databaseSeed = new DatabaseSeedsRepository();

    void add(Object obj);
    List<Object> getAll();
    List<Object> find(String name);


}
