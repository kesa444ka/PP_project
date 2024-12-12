package com.mypackage.encryptionTest;

import com.mypackage.encryption.EncryptionModule;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

public class EncryptionModuleTest {

    private static final String TEST_INPUT_FILE = "input.txt";
    private static final String CONTENT = "Some text";

    @BeforeEach
    void setUp() throws IOException {
        // Создаем тестовый файл с содержимым
        Files.writeString(Paths.get(TEST_INPUT_FILE), CONTENT);
    }

    @AfterEach
    void tearDown() throws IOException {
        // Удаляем тестовый файл после тестов, если он существует
        Files.deleteIfExists(Paths.get(TEST_INPUT_FILE));
    }

    @Test
    void testEncryptFile() throws IOException {
        // Проверяем, что файл успешно шифруется
        EncryptionModule.encrypt(TEST_INPUT_FILE);
        assertTrue(EncryptionModule.isEncrypted(TEST_INPUT_FILE));
    }

    @Test
    void testDecryptFile() throws IOException {
        // Шифруем файл
        EncryptionModule.encrypt(TEST_INPUT_FILE);
        assertTrue(EncryptionModule.isEncrypted(TEST_INPUT_FILE));

        // Расшифровываем файл
        EncryptionModule.decrypt(TEST_INPUT_FILE);
        assertFalse(EncryptionModule.isEncrypted(TEST_INPUT_FILE));
    }

    @Test
    void testIsEncrypted() throws IOException {
        // Убедиться, что файл не зашифрован до начала теста
        assertFalse(EncryptionModule.isEncrypted(TEST_INPUT_FILE));

        // Зашифровать файл и проверить
        EncryptionModule.encrypt(TEST_INPUT_FILE);
        assertTrue(EncryptionModule.isEncrypted(TEST_INPUT_FILE));
    }
}
