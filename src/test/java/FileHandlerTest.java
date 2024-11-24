import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.nio.file.*;

import org.jdom2.JDOMException;

public class FileHandlerTest {

    private static final String TEST_DIRECTORY = "test_files";
    private static final String TEST_INPUTFILE_TXT = TEST_DIRECTORY + "/test.txt";
    private static final String TEST_INPUTFILE_XML = TEST_DIRECTORY + "/test.xml";
    private static final String TEST_INPUTFILE_JSON = TEST_DIRECTORY + "/test.json";
    private static final String TEST_OUTPUTFILE_TXT = TEST_DIRECTORY + "/test_output.txt";
    private static final String TEST_OUTPUTFILE_XML = TEST_DIRECTORY + "/test_output.xml";
    private static final String TEST_OUTPUTFILE_JSON = TEST_DIRECTORY + "/test_output.json";
    private static final String UNSUPPORTED_FILE = TEST_DIRECTORY + "/unsupported.csv";

    @BeforeAll
    static void setup() throws IOException {
        Files.createDirectories(Paths.get(TEST_DIRECTORY));
        Files.writeString(Paths.get(TEST_INPUTFILE_TXT), "2 + 3\n7 - 4");
        Files.writeString(Paths.get(UNSUPPORTED_FILE), "unsupported");

        String xmlContent = """
            <mathExamples>
                <example>
                    <expression>2 + 3</expression>
                </example>
                <example>
                    <expression>7 - 4</expression>
                </example>
            </mathExamples>
            """;
        Files.writeString(Paths.get(TEST_INPUTFILE_XML), xmlContent);

        String jsonContent = """
            {
                "mathExamples":
                [
                    {
                        "expression": "2 + 3" 
                    },
                    { 
                        "expression": "7 - 4" 
                    }
                ]
            }
            """;
        Files.writeString(Path.of(TEST_INPUTFILE_JSON), jsonContent);
    }

    @AfterAll
    static void cleanup() throws IOException {
        // Удаляем все тестовые файлы и директории
        Files.deleteIfExists(Paths.get(TEST_INPUTFILE_TXT));
        Files.deleteIfExists(Paths.get(TEST_INPUTFILE_XML));
        Files.deleteIfExists(Paths.get(TEST_INPUTFILE_JSON));
        Files.deleteIfExists(Paths.get(TEST_OUTPUTFILE_TXT));
        Files.deleteIfExists(Paths.get(TEST_OUTPUTFILE_XML));
        Files.deleteIfExists(Paths.get(TEST_OUTPUTFILE_JSON));
        Files.deleteIfExists(Paths.get(UNSUPPORTED_FILE));
        Files.deleteIfExists(Paths.get(TEST_DIRECTORY));
    }

    @Test
    void ReadTxtFile() throws IOException, JDOMException {
        String expected = "2 + 3\n7 - 4";
        String actual = FileHandler.readFile(TEST_INPUTFILE_TXT, "txt");
        assertEquals(expected, actual);
    }

    @Test
    void ReadXmlFile() throws IOException, JDOMException {
        String content = FileHandler.readFile(TEST_INPUTFILE_XML, "xml");
        assertEquals("2 + 3\n7 - 4", content);
    }

    @Test
    void ReadJsonFile() throws IOException, JDOMException {
        String content = FileHandler.readFile(TEST_INPUTFILE_JSON, "json");
        assertEquals("2 + 3\n7 - 4", content);
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
    void testWriteXmlFile() throws IOException {
        String data = "5\n3";
        FileHandler.writeFile(TEST_OUTPUTFILE_XML, data, "xml");

        assertTrue(Files.exists(Path.of(TEST_OUTPUTFILE_XML)));

        String writtenContent = Files.readString(Path.of(TEST_OUTPUTFILE_XML));
        assertTrue(writtenContent.contains("<result>5</result>"));
        assertTrue(writtenContent.contains("<result>3</result>"));
    }

    @Test
    void testWriteJsonFile() throws IOException {
        String data = "5\n3";
        FileHandler.writeFile(TEST_OUTPUTFILE_JSON, data, "json");
        assertTrue(Files.exists(Path.of(TEST_OUTPUTFILE_JSON)));
        String writtenContent = Files.readString(Path.of(TEST_OUTPUTFILE_JSON));

        assertTrue(writtenContent.contains("\"result\" : \"5\""));
        assertTrue(writtenContent.contains("\"result\" : \"3\""));
    }

    @Test
    void WriteUnsupportedFileType() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> FileHandler.writeFile(TEST_OUTPUTFILE_TXT, "Dummy data", "unsupported"),
                "Ожидалось исключение IllegalArgumentException для неподдерживаемого типа файла."
        );
        // Проверяем сообщение исключения
        assertEquals("Неподдерживаемый тип файла: unsupported", e.getMessage());
    }

}
