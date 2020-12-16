package be.howest.ti.mars.logic.controller;

import be.howest.ti.mars.logic.data.MarsRepository;
import be.howest.ti.mars.logic.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MarsControllerTest {


    MarsController marsController;
    @BeforeAll
    void setup() throws SQLException {
        MarsRepository.configure("jdbc:h2:~/mars-db", "group-14", "t3sfe1k3nUe", 9000);
    }

    @BeforeEach
    void init() throws IOException, SQLException {
        createDatabase();
        marsController = new MarsController();
    }

    private void createDatabase() throws IOException, SQLException {
        executeScript("src/test/recourses/databaseTest.sql");
    }

    private void executeScript(String fileName) throws IOException, SQLException {
        String createDbSql = readFile(fileName);
        try (
                Connection con = MarsRepository.getConnection();
                PreparedStatement stmt = con.prepareStatement(createDbSql)
        ) {
            stmt.executeUpdate();
        }
    }

    private String readFile(String fileName) throws IOException {
        Path file = Path.of(fileName);
        return Files.readString(file);
    }

    @Test
    void getMessageReturnsAWelcomeMessage() {
        // Arrange
        MarsController sut = new MarsController();

        // Act
        String message = sut.getMessage();

        //Assert
        assertTrue(StringUtils.isNoneBlank(message));
    }

    @Test
    void getCropByLocationTest() {
        List<CropTypes> cropTypes = marsController.getCropByLocation(-2, 3);
        CropTypes cropTypes1 = new CropTypes(-2.8472767, 2.218816, "Apple", "fruit", 5);
        CropTypes cropTypes2 = new CropTypes(-1.8567844, 3.213108, "Apple", "fruit", 6);
        assertTrue(cropTypes.contains(cropTypes1));
        assertTrue(cropTypes.contains(cropTypes2));
    }

    @Test
    void updateUserInfoTest(){
        Boolean check = marsController.updateUser("Alice", "Bob", "Alice@Bob.com", LocalDate.of(2000, 1,1), 1);
        marsController.updateAddress("Foo", 403, "12345", 1);
        assertTrue(check);
    }

    @Test
    void getUserTest(){
        User sys = new User(1,"Kurt", "Sys", "Kurt.Sys@hotmail.com", LocalDate.of(2030, 5, 20), new Subscription(SubscriptionType.PREMIUM),new Address(1, "The Moon", 404, "1337"));
        User sys2 = marsController.getUserById(1);
        assertEquals(sys, sys2);
    }


}
