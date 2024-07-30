package by.aston.bank.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {

    private static final Properties PROPERTIES = new Properties();

    static {
        initProperties();
    }

    private static void initProperties() {
        try (var property = PropertiesManager.class.getClassLoader().getResourceAsStream("application-test.properties")) {
            PROPERTIES.load(property);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

}
