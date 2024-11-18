import java.nio.file.*;
//import java.util.*;
import java.io.*;

public class FileHandler {

    // Чтение данных из файла
    public static String readFile(String fileName, String type) throws IOException {
        String content = Files.readString(Paths.get(fileName));
        switch (type) {
            case "txt":
                return content;
            default:
                throw new IllegalArgumentException("Неподдерживаемый тип файла: " + type);
        }
    }

    // Запись данных в файл
    public static void writeFile(String path, String data, String type) throws IOException {
        switch (type.toLowerCase()) {
            case "txt":
                Files.writeString(Paths.get(path), data);
                break;
            default:
                throw new IllegalArgumentException("Неподдерживаемый тип файла: " + type);
        }
    }
}
