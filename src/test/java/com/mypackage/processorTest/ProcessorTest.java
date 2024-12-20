package com.mypackage.processorTest;

import com.mypackage.processor.Processor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProcessorTest {
    @Test
    void testProcessWithoutRegex() {
        String input = """
                2+2
                5*(3-1)
                36/6+2*(5-3)""";
        String expectedOutput = """
                4
                10
                10""";

        String result = Processor.getResult(input, 1);

        assertEquals(expectedOutput, result);
    }

    @Test
    void testCalculateWithRegex() {
        assertEquals("4", Processor.getResult("2+2",2));
        assertEquals("10", Processor.getResult("5*(3-1)", 2));
        assertEquals("10", Processor.getResult("36/6+2*(5-3)", 2));
    }

    @Test
    void testProcessWithRegex() {
        String input = """
                2+2
                5*(3-1)
                36/6+2*(5-3)""";
        String expectedOutput = """
                4
                10
                10""";

        String result = Processor.getResult(input, 2);

        assertEquals(expectedOutput, result);
    }

    @Test
    void testProcessLibrary() {
        String input = """
                2+2
                5*(3-1)
                36/6+2*(5-3)""";
        String expectedOutput = """
                4
                10
                10""";

        String result = Processor.getResult(input, 3);

        assertEquals(expectedOutput, result);
    }
}
