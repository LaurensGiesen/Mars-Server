package be.howest.ti.mars.logic.controller;

import be.howest.ti.mars.logic.data.MarsRepository;
import be.howest.ti.mars.logic.domain.CropTypes;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MarsControllerTest {


    @BeforeAll
    void setup() throws SQLException {
        MarsRepository.configure("jdbc:h2:~/mars-db", "group-14", "t3sfe1k3nUe", 9000);
    }

    @BeforeEach
    void init() throws IOException, SQLException {
        createDatabase();
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
        MarsController marsController = new MarsController();
        List<CropTypes> cropTypes = marsController.getCropByLocation(-2, 3);
        CropTypes cropTypes1 = new CropTypes(-2.8472767, 2.218816, "Apple", "fruit", 5);
        CropTypes cropTypes2 = new CropTypes(-1.8567844, 3.213108, "Apple", "fruit", 6);
        assertTrue(cropTypes.contains(cropTypes1));
        assertTrue(cropTypes.contains(cropTypes2));
    }

}
