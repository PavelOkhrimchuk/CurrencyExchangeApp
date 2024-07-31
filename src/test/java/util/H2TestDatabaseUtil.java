package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class H2TestDatabaseUtil {

    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            loadProperties();
            // Явная регистрация драйвера
            Class.forName(PROPERTIES.getProperty("jdbc.driver"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load properties file or driver.", e);
        }
    }

    private static void loadProperties() throws IOException {
        try (InputStream inputStream = H2TestDatabaseUtil.class.getClassLoader().getResourceAsStream("test.properties")) {
            if (inputStream != null) {
                PROPERTIES.load(inputStream);
            } else {
                throw new IOException("Properties file not found.");
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", PROPERTIES.getProperty("jdbc.username"));
        connectionProps.put("password", PROPERTIES.getProperty("jdbc.password"));

        return DriverManager.getConnection(
                PROPERTIES.getProperty("jdbc.url"),
                connectionProps
        );
    }

}
