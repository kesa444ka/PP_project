import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.nio.file.*;

public class FileHandlerTest {

    private static final String TEST_DIRECTORY = "test_files";
    private static final String TEST_INPUTFILE_TXT = TEST_DIRECTORY + "/test.txt";
    private static final String TEST_OUTPUTFILE_TXT = TEST_DIRECTORY + "/test_output.txt";
    private static final String UNSUPPORTED_FILE = TEST_DIRECTORY + "/unsupported.csv";

    @BeforeAll
    static void setup() throws IOException {
        // Создаём временную папку для тестовых файлов
        Files.createDirectories(Paths.get(TEST_DIRECTORY));
        // Создаём тестовый txt файл
        Files.writeString(Paths.get(TEST_INPUTFILE_TXT), "Hello, world!");
        // Создаём файл с неподдерживаемым форматом
        Files.writeString(Paths.get(UNSUPPORTED_FILE), "{\"key\": \"value\"}");
    }

    @AfterAll
    static void cleanup() throws IOException {
        // Удаляем все тестовые файлы и директории
        Files.deleteIfExists(Paths.get(TEST_INPUTFILE_TXT));
        Files.deleteIfExists(Paths.get(TEST_OUTPUTFILE_TXT));
        Files.deleteIfExists(Paths.get(UNSUPPORTED_FILE));
        Files.deleteIfExists(Paths.get(TEST_DIRECTORY));
    }

    @Test
    void ReadTxtFile() throws IOException {
        String expected = "Hello, world!";
        String actual = FileHandler.readFile(TEST_INPUTFILE_TXT, "txt");
        assertEquals(expected, actual);
    }

    @Test
    void ReadUnsupportedFile() {
        // Проверяем, что метод readFile выбрасывает IllegalArgumentException
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, // Ожидаемый тип исключения
                () -> FileHandler.readFile(UNSUPPORTED_FILE, "unsupported"), // Тестируемый код
                "Ожидалось исключение IllegalArgumentException" //Тест завершится в случае неудачи и выдаст это
        );

        // Проверяем сообщение исключения
        assertEquals(
                "Неподдерживаемый тип файла: unsupported", // Ожидаемое сообщение
                e.getMessage(), // Сообщение из выброшенного исключения
                "Exception message should match the expected one."
        );
    }

    @Test
    void WriteTxtFile() throws IOException {
        // Данные для записи
        String testData = "Hello, world!";
        // Записываем данные в файл
        FileHandler.writeFile(TEST_OUTPUTFILE_TXT, testData, "txt");
        // Проверяем, что файл существует
        assertTrue(Files.exists(Paths.get(TEST_OUTPUTFILE_TXT)), "Файл должен быть создан.");
        // Проверяем содержимое файла
        String actualContent = Files.readString(Paths.get(TEST_OUTPUTFILE_TXT));
        assertEquals(testData, actualContent, "Содержимое файла должно совпадать с записанными данными.");
    }

    @Test
    void WriteUnsupportedFileType() {
        // Проверяем, что неподдерживаемый тип вызывает исключение
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> FileHandler.writeFile(TEST_OUTPUTFILE_TXT, "Dummy data", "unsupported"),
                "Ожидалось исключение IllegalArgumentException для неподдерживаемого типа файла."
        );
        // Проверяем сообщение исключения
        assertEquals("Неподдерживаемый тип файла: unsupported", e.getMessage());
    }

}
