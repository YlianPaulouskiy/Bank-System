package by.aston.bank.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@UtilityClass
public class ConnectionManager {

    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String DRIVER_KEY = "driver";
    private static final String URL_KEY = "url";

    static {
        loadDriver();
    }

    private static void loadDriver() {
        try {
            Class.forName(PropertiesUtils.get(DRIVER_KEY));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtils.get(URL_KEY), PropertiesUtils.get(USERNAME_KEY), PropertiesUtils.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
