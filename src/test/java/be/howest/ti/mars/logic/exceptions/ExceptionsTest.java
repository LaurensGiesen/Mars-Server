package be.howest.ti.mars.logic.exceptions;
import be.howest.ti.mars.logic.data.DatabaseMapRepository;
import be.howest.ti.mars.logic.data.DatabaseProductRepository;
import be.howest.ti.mars.logic.data.DatabaseUsersRepository;
import be.howest.ti.mars.logic.data.MarsRepository;
import be.howest.ti.mars.logic.domain.*;
import be.howest.ti.mars.logic.unit.Config;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExceptionsTest {

    @BeforeAll
    void setup() throws SQLException {
        MarsRepository.configure("jdbc:h2:~/mars-db", "group-14", "t3sfe1k3nUe", 9001);
    }

    @BeforeEach
    void init() throws IOException, SQLException {
        createDatabase();
    }

    private void createDatabase() throws SQLException {
        Config.getInstance().executeScript("databaseTest.sql");
    }

    @Test
    void ProductExceptionTest(){
        DatabaseProductRepository databaseProductRepository = new DatabaseProductRepository();
        DatabaseUsersRepository databaseUsersRepository = new DatabaseUsersRepository();
        assertThrows(ProductException.class, () -> databaseProductRepository.find("foo", null));
        assertThrows(ProductException.class, () -> databaseProductRepository.removeProduct(new Product(1, null, 1, null)));
        assertThrows(ProductException.class, () -> databaseUsersRepository.updateProductOfUser(1,null,1, null));
    }

    @Test
    void SubscriptionExceptionTest(){
        DatabaseMapRepository databaseMapRepository = new DatabaseMapRepository();
        List<CropTypes> cropTypesList = new LinkedList<>();
        cropTypesList.add(new CropTypes(1,2, "foo", "fruit",1));
        assertThrows(SubscriptionException.class, () -> databaseMapRepository.getCropTypesForUsersSubscription(cropTypesList, null));
    }
}
