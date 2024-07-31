package util;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        executeSqlScript("schema.sql");
        executeSqlScript("data.sql");
    }

    private static void executeSqlScript(String fileName) {
        try (Connection connection = H2TestDatabaseUtil.getConnection();
             Statement statement = connection.createStatement()) {

            String sql = readSqlScript(fileName);
            statement.execute(sql);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute SQL script: " + fileName, e);
        }
    }

    private static String readSqlScript(String fileName) {
        try (InputStream inputStream = DatabaseInitializer.class.getClassLoader().getResourceAsStream(fileName);
             Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {

            return scanner.useDelimiter("\\A").next();

        } catch (Exception e) {
            throw new RuntimeException("Failed to read SQL script: " + fileName, e);
        }
    }
}
