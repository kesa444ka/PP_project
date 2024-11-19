import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProcessorTest {

    @Test
    void testProcessValidExpressions() {
        String input = "2+2\n" +
                "5*(3-1)\n" +
                "36/6+2*(5-3)";
        String expectedOutput = "4\n" +
                "10\n" +
                "10";

        String result = Processor.process(input, 1);

        assertEquals(expectedOutput, result);
    }

    @Test
    void testInfixToPostfix() {
        assertEquals("2 2 +", Processor.InfixToPostfix("2+2"));
        assertEquals("5 3 1 - *", Processor.InfixToPostfix("5*(3-1)"));
        assertEquals("36 6 / 2 5 3 - * +", Processor.InfixToPostfix("36/6+2*(5-3)"));
    }

    @Test
    void testCalculate() {
        assertEquals(4, Processor.calculate("2 2 +"));
        assertEquals(10, Processor.calculate("5 3 1 - *"));
        assertEquals(10, Processor.calculate("36 6 / 2 5 3 - * +"));
    }
}
