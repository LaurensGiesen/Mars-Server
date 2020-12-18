package be.howest.ti.mars.logic.unit;

import be.howest.ti.mars.logic.data.MarsRepository;
import be.howest.ti.mars.logic.exceptions.ConfigException;
import be.howest.ti.mars.logic.exceptions.ProductException;

import java.io.*;
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

    public String readFile(String fileName) {
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
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Cannot read file: " + fileName, ex);
            throw new ConfigException("Cannot read file: " + fileName);
        }
    }

    public byte[] getFile(String filename) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            try {
                return inputStream.readAllBytes();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to get file");
                throw new ConfigException("Failed to get file");
            }
        }
    }
}
