package com.mypackage.fileprocessorTest;

import com.mypackage.fileprocessor.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.nio.file.*;

public class FileProcessorTest {

    private static final String TEST_DIRECTORY = "test_files";
    private static final String TEST_INPUTFILE_TXT = TEST_DIRECTORY + "/test.txt";
    private static final String TEST_INPUTFILE_XML = TEST_DIRECTORY + "/test.xml";
    private static final String TEST_INPUTFILE_JSON = TEST_DIRECTORY + "/test.json";
    private static final String TEST_INPUTFILE_YAML = TEST_DIRECTORY + "/test.yaml";
    private static final String TEST_OUTPUTFILE_TXT = TEST_DIRECTORY + "/test_output.txt";
    private static final String TEST_OUTPUTFILE_XML = TEST_DIRECTORY + "/test_output.xml";
    private static final String TEST_OUTPUTFILE_JSON = TEST_DIRECTORY + "/test_output.json";
    private static final String TEST_OUTPUTFILE_YAML = TEST_DIRECTORY + "/test_output.yaml";

    @BeforeAll
    static void setup() throws IOException {
        Files.createDirectories(Paths.get(TEST_DIRECTORY));
        Files.writeString(Paths.get(TEST_INPUTFILE_TXT), "2 + 3\n7 - 4");

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

        String yamlContent = """
                mathExamples:
                  - expression: "2 + 3"
                  - expression: "7 - 4"
                """;
        Files.writeString(Path.of(TEST_INPUTFILE_YAML), yamlContent);
    }

    @AfterAll
    static void cleanup() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_INPUTFILE_TXT));
        Files.deleteIfExists(Paths.get(TEST_INPUTFILE_XML));
        Files.deleteIfExists(Paths.get(TEST_INPUTFILE_JSON));
        Files.deleteIfExists(Paths.get(TEST_INPUTFILE_YAML));
        Files.deleteIfExists(Paths.get(TEST_OUTPUTFILE_TXT));
        Files.deleteIfExists(Paths.get(TEST_OUTPUTFILE_XML));
        Files.deleteIfExists(Paths.get(TEST_OUTPUTFILE_JSON));
        Files.deleteIfExists(Paths.get(TEST_OUTPUTFILE_YAML));
        Files.deleteIfExists(Paths.get(TEST_DIRECTORY));
    }

    @Test
    void ReadTxtFile() throws Exception{
        String actual = FileProcessor.readFile(TEST_INPUTFILE_TXT);
        assertEquals("2 + 3\n7 - 4", actual);
    }

    @Test
    void ReadXmlFile() throws Exception {
        String content = FileProcessor.readFile(TEST_INPUTFILE_XML);
        assertEquals("2 + 3\n7 - 4", content);
    }

    @Test
    void ReadJsonFile() throws Exception{
        String content = FileProcessor.readFile(TEST_INPUTFILE_JSON);
        assertEquals("2 + 3\n7 - 4", content);
    }

    @Test
    void ReadYamlFile() throws Exception {
        String content = FileProcessor.readFile(TEST_INPUTFILE_YAML);
        assertEquals("2 + 3\n7 - 4", content);
    }

    @Test
    void WriteTxtFile() throws Exception {
        String testData = "Hello, world!";
        FileProcessor.writeFile(TEST_OUTPUTFILE_TXT, testData);
        Path path = Paths.get(TEST_OUTPUTFILE_TXT);
        assertTrue(Files.exists(path), "Файл должен быть создан.");
        String actualContent = Files.readString(path);
        assertEquals(testData, actualContent, "Содержимое файла должно совпадать с записанными данными.");
    }

    @Test
    void testWriteXmlFile() throws Exception {
        String data = "5\n3";
        FileProcessor.writeFile(TEST_OUTPUTFILE_XML, data);

        Path path = Path.of(TEST_OUTPUTFILE_XML);
        assertTrue(Files.exists(path));

        String writtenContent = Files.readString(path);
        assertTrue(writtenContent.contains("<result>5</result>"));
        assertTrue(writtenContent.contains("<result>3</result>"));
    }

    @Test
    void testWriteJsonFile() throws Exception {
        String data = "5\n3";
        FileProcessor.writeFile(TEST_OUTPUTFILE_JSON, data);
        Path path = Path.of(TEST_OUTPUTFILE_JSON);
        assertTrue(Files.exists(path));
        String writtenContent = Files.readString(path);

        assertTrue(writtenContent.contains("\"result\" : \"5\""));
        assertTrue(writtenContent.contains("\"result\" : \"3\""));
    }

    @Test
    void testWriteYamlFile() throws Exception {
        String data = "5\n3";
        FileProcessor.writeFile(TEST_OUTPUTFILE_YAML, data);
        Path path = Path.of(TEST_OUTPUTFILE_YAML);
        assertTrue(Files.exists(path));
        String writtenContent = Files.readString(path);

        assertTrue(writtenContent.contains("- result: '5'"));
        assertTrue(writtenContent.contains("- result: '3'"));
    }
}
