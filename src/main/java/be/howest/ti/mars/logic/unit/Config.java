package be.howest.ti.mars.logic.unit;

import be.howest.ti.mars.logic.data.MarsRepository;
import be.howest.ti.mars.logic.exceptions.ConfigException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {

    private static final Config INSTANCE = new Config();
    private static final Logger LOGGER = Logger.getLogger(Config.class.getName());

    public static Config getInstance() {
        return INSTANCE;
    }

    public void executeScript(String fileName) throws SQLException {
        String createDbSql = readFile(fileName);
        try (
                Connection con = MarsRepository.getConnection();
                PreparedStatement stmt = con.prepareStatement(createDbSql)
        ) {
            stmt.executeUpdate();
        }
    }

    private String readFile(String fileName) {
        try (InputStream recourse = getClass().getClassLoader().getResourceAsStream(fileName)) {
            assert recourse != null;
            try (BufferedReader r = new BufferedReader(new InputStreamReader(recourse))) {
                StringBuilder res = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    res.append(line);

                }
                return res.toString();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Cannot read file: " + fileName, e);
            throw new ConfigException("Cannot read file: " + fileName);
        }
    }
}
