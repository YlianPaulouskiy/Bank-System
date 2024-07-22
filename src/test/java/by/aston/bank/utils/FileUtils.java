package by.aston.bank.utils;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@UtilityClass
public class FileUtils {

    public String read(Path filePath) {
        try {
            return Files.readString(filePath);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return "";
    }

}
