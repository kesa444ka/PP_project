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

        String result = Processor.calculate(input, 1);

        assertEquals(expectedOutput, result);
    }

    @Test
    void testCalculateWithRegex() {
        assertEquals("4", Processor.calculate("2+2",2));
        assertEquals("10", Processor.calculate("5*(3-1)", 2));
        assertEquals("10", Processor.calculate("36/6+2*(5-3)", 2));
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

        String result = Processor.calculate(input, 2);

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

        String result = Processor.calculate(input, 3);

        assertEquals(expectedOutput, result);
    }
}
