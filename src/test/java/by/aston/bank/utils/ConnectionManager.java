package by.aston.bank.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static final String URL_KEY = "db.url";

    static {
        loadDriver();
    }

    private static void loadDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(PropertiesManager.get(URL_KEY),
                    PropertiesManager.get(USERNAME_KEY),
                    PropertiesManager.get(PASSWORD_KEY));
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
