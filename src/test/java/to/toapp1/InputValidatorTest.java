package to.toapp1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

class InputValidatorTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setOut(System.out);
    }

    @Test
    public void testValidMonthlySalaryInput() {
        String input = "100.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        double result = InputValidator.getValidMonthlySalary(scanner);

        assertEquals(100.0, result);
    }

    @Test
    public void testNegativeMonthlySalaryInput() {
        String input = "-100.0\n100.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        double result = InputValidator.getValidMonthlySalary(scanner);

        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("cannot be negative"));
        assertEquals(100.0, result);
    }

    @Test
    public void testInvalidSalaryInputText() {
        String input = "text\n100.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        double result = InputValidator.getValidMonthlySalary(scanner);

        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Invalid input"));

        assertEquals(100.0, result);
    }

    @Test
    public void testEmptySalaryInput() {
        String input = "\n100.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        double result = InputValidator.getValidMonthlySalary(scanner);

        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Invalid input"));

        assertEquals(100.0, result);
    }

    @Test
    public void testInvalidSalaryInputNumberAndText() {
        String input = "200 text\n100.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        double result = InputValidator.getValidMonthlySalary(scanner);

        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Invalid input"));

        assertEquals(100.0, result);
    }

    @Test
    public void testSalaryInputInt() {
        String input = "200\n100.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        double result = InputValidator.getValidMonthlySalary(scanner);

        String consoleOutput = outContent.toString();
        assertFalse(consoleOutput.contains("Invalid input"));

        assertEquals(200.0, result);
    }

    @Test
    public void testValidIdInput() {
        String input = "31\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        Long result = InputValidator.getValidId(scanner);

        assertEquals(31, result);
    }

    @Test
    public void testNegativeIdInput() {
        String input = "-21\n31\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        Long result = InputValidator.getValidId(scanner);

        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Invalid ID"));

        assertEquals(31, result);
    }

    @Test
    public void testInvalidIdInputText() {
        String input = "String 21\n31\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        Long result = InputValidator.getValidId(scanner);

        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Invalid ID"));

        assertEquals(31, result);
    }

    @Test
    public void testInvalidIdInputDouble() {
        String input = "21.0\n31\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        Long result = InputValidator.getValidId(scanner);

        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Invalid ID"));

        assertEquals(31, result);
    }

    @Test
    public void testValidStringInput() {
        String input = "String\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        String result = InputValidator.getValidString(scanner);

        assertEquals("String", result);
    }

    @Test
    public void testInvalidStringInputNumbers() {
        String input = "123String\nString\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        String result = InputValidator.getValidString(scanner);

        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Invalid input"));

        assertEquals("String", result);
    }

    @Test
    public void testInvalidStringInputWithSpecialCharacters() {
        String input = "$String\nString\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        String result = InputValidator.getValidString(scanner);

        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Invalid input"));

        assertEquals("String", result);
    }

    @Test
    public void testEditingValidStringInput() {
        String input = "stRIng\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        String result = InputValidator.getValidString(scanner);

        assertEquals("String", result);
    }

    @Test
    public void testValidInputUpdatedSalary() {
        String input = "100.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        double result = InputValidator.getValidUpdatedMonthlySalary("200.0\n", scanner);

        assertEquals(200.0, result);
    }

    @Test
    public void testNegativeStringUpdatedSalary() {
        String input = "100.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        double result = InputValidator.getValidUpdatedMonthlySalary("-200.0\n", scanner);

        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("cannot be negative"));

        assertEquals(100.0, result);
    }

    @Test
    public void testInvalidStringUpdatedSalary() {
        String input = "100.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        double result = InputValidator.getValidUpdatedMonthlySalary("String\n", scanner);

        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Invalid input"));

        assertEquals(100.0, result);
    }

    @Test
    public void testNegativeNumberAndNegativeScanerUpdatedSalary() {
        String input = "-100.0\n300.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        double result = InputValidator.getValidUpdatedMonthlySalary("-200.0", scanner);

        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("cannot be negative"));

        assertEquals(300.0, result);
    }

    @Test
    public void testInvalidNumberAndInvalidScanerUpdatedSalary() {
        String input = "String 10.0\n200.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        double result = InputValidator.getValidUpdatedMonthlySalary("String 100.0", scanner);

        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Invalid input"));

        assertEquals(200.0, result);
    }

    @Test
    public void testIntNumberUpdatedSalary() {
        String input = "200.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        double result = InputValidator.getValidUpdatedMonthlySalary("100", scanner);

        assertEquals(100.0, result);
    }

    @Test
    public void testValidInputUpdatedString() {
        String input = "String\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        String result = InputValidator.getValidUpdatedString("String", scanner);

        assertEquals("String", result);
    }

    @Test
    public void testInvalidParameterAndValidScannerUpdatedString() {
        String input = "String\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        String result = InputValidator.getValidUpdatedString("12String", scanner);

        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Invalid input"));

        assertEquals("String", result);
    }

    @Test
    public void testValidParameterAndInvalidScannerUpdatedString() {
        String input = "12String\nString\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        String result = InputValidator.getValidUpdatedString("String", scanner);

        assertEquals("String", result);
    }

    @Test
    public void testInvalidParameterAndInvalidScannerUpdatedString() {
        String input = "12String\nString\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        String result = InputValidator.getValidUpdatedString("S123tring", scanner);

        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Invalid input"));

        assertEquals("String", result);
    }

    @Test
    public void testRightTextFromParameterEditingUpdatedString() {
        String input = "Scaner\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        String result = InputValidator.getValidUpdatedString("strINg", scanner);

        assertEquals("String", result);
    }

    @Test
    public void testRightTextFromScanerEditingUpdatedString() {
        String input = "sCaNEr\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        String result = InputValidator.getValidUpdatedString("strINg?", scanner);

        assertEquals("Scaner", result);
    }

}
