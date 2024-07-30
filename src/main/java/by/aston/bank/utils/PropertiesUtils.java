package by.aston.bank.utils;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public final class PropertiesUtils {

    private final static Properties PROPERTY = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream inputStream = PropertiesUtils.class.getClassLoader()
                .getResourceAsStream("liquibase.properties")) {
            PROPERTY.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return PROPERTY.getProperty(key);
    }

}
