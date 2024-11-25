import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

class CompressionModuleTest {

    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    private static final String TEST_INPUT_FILE = TEMP_DIR + "input.txt";
    private static final String COMPRESSED_FILE = TEMP_DIR + "sample.zip";
    private static final String OUTPUT_DIR = TEMP_DIR + "output/";
    private static final String REQUIRED_EXTENSION = ".txt";

    @BeforeEach
    void setUp() throws IOException {
        // Создаем временную папку для тестов
        Files.createDirectories(Paths.get(OUTPUT_DIR));

        // Создаем тестовый файл
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_INPUT_FILE))) {
            writer.write("Some text");
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        // Удаляем все созданные файлы
        Files.deleteIfExists(Paths.get(TEST_INPUT_FILE));
        Files.deleteIfExists(Paths.get(COMPRESSED_FILE));
        Files.walk(Paths.get(OUTPUT_DIR)).map(Path::toFile).forEach(File::delete); //удаление всех файлов в каталоге
        Files.deleteIfExists(Paths.get(OUTPUT_DIR));
    }

    @Test
    void testCompress() throws IOException {
        // Проверяем, что файл сжат успешно
        CompressionModule.compress(TEST_INPUT_FILE, COMPRESSED_FILE);
        assertTrue(Files.exists(Paths.get(COMPRESSED_FILE)));
    }

    @Test
    void testDecompress() throws IOException {
        // Сжимаем файл, а затем извлекаем его
        CompressionModule.compress(TEST_INPUT_FILE, COMPRESSED_FILE);
        String extractedFilePath = CompressionModule.decompress(COMPRESSED_FILE, OUTPUT_DIR, REQUIRED_EXTENSION);

        // Проверяем, что файл был извлечен
        assertTrue(Files.exists(Paths.get(extractedFilePath)));

        // Проверяем содержимое извлеченного файла
        String content = Files.readString(Paths.get(extractedFilePath));
        assertEquals("Some text", content);
    }
}
