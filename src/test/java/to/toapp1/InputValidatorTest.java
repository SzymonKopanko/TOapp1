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
        //arrange
        String input = "100.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        //act
        double result = InputValidator.getValidMonthlySalary(scanner);

        //assert
        assertEquals(100.0, result);
    }

    @Test
    public void testNegativeMonthlySalaryInput() {
        //arrange
        String input = "-100.0\n100.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        //act
        double result = InputValidator.getValidMonthlySalary(scanner);

        //assert
        assertTrue(outContent.toString().contains("cannot be negative"));
        assertEquals(100.0, result);
    }

    @Test
    public void testInvalidSalaryInputText() {
        //arrange
        String input = "text\n100.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        //act
        double result = InputValidator.getValidMonthlySalary(scanner);

        //assert
        assertTrue(outContent.toString().contains("Invalid input"));
        assertEquals(100.0, result);
    }

    @Test
    public void testEmptySalaryInput() {
        //arrange
        String input = "\n100.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        //act
        double result = InputValidator.getValidMonthlySalary(scanner);

        //assert
        assertTrue(outContent.toString().contains("Invalid input"));
        assertEquals(100.0, result);
    }

    @Test
    public void testInvalidSalaryInputNumberAndText() {
        //arrange
        String input = "200 text\n100.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        //act
        double result = InputValidator.getValidMonthlySalary(scanner);

        //assert
        assertTrue(outContent.toString().contains("Invalid input"));
        assertEquals(100.0, result);
    }

    @Test
    public void testSalaryInputInt() {
        //arrange
        String input = "200\n100.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        //act
        double result = InputValidator.getValidMonthlySalary(scanner);

        //assert
        assertFalse(outContent.toString().contains("Invalid input"));
        assertEquals(200.0, result);
    }

    @Test
    public void testValidIdInput() {
        //arrange
        String input = "31\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        //act
        Long result = InputValidator.getValidId(scanner);

        //assert
        assertEquals(31, result);
    }

    @Test
    public void testNegativeIdInput() {
        //arrange
        String input = "-21\n31\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        //act
        Long result = InputValidator.getValidId(scanner);

        //assert
        assertTrue(outContent.toString().contains("Invalid ID"));
        assertEquals(31, result);
    }

    @Test
    public void testInvalidIdInputText() {
        //arrange
        String input = "String 21\n31\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        //act
        Long result = InputValidator.getValidId(scanner);

        //assert
        assertTrue(outContent.toString().contains("Invalid ID"));
        assertEquals(31, result);
    }

    @Test
    public void testInvalidIdInputDouble() {
        //arrange
        String input = "21.0\n31\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        //act
        Long result = InputValidator.getValidId(scanner);

        //assert
        assertTrue(outContent.toString().contains("Invalid ID"));
        assertEquals(31, result);
    }

    @Test
    public void testValidStringInput() {
        //arrange
        String input = "String\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        //act
        String result = InputValidator.getValidString(scanner);

        //assert
        assertEquals("String", result);
    }

    @Test
    public void testInvalidStringInputNumbers() {
        //arrange
        String input = "123String\nString\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        //act
        String result = InputValidator.getValidString(scanner);

        //assert
        assertTrue(outContent.toString().contains("Invalid input"));
        assertEquals("String", result);
    }

    @Test
    public void testInvalidStringInputWithSpecialCharacters() {
        //arrange
        String input = "$String\nString\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        //act
        String result = InputValidator.getValidString(scanner);

        //assert
        assertTrue(outContent.toString().contains("Invalid input"));
        assertEquals("String", result);
    }

    @Test
    public void testEditingValidStringInput() {
        //arrange
        String input = "stRIng\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        //act
        String result = InputValidator.getValidString(scanner);

        //assert
        assertEquals("String", result);
    }

    @Test
    public void testValidInputUpdatedSalary() {
        //arrange
        String input = "100.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        //act
        double result = InputValidator.getValidUpdatedMonthlySalary("200.0\n", scanner);

        //assert
        assertEquals(200.0, result);
    }

    @Test
    public void testNegativeStringUpdatedSalary() {
        //arrange
        String input = "100.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        //act
        double result = InputValidator.getValidUpdatedMonthlySalary("-200.0\n", scanner);

        //assert
        assertTrue(outContent.toString().contains("cannot be negative"));
        assertEquals(100.0, result);
    }

    @Test
    public void testInvalidStringUpdatedSalary() {
        //arrange
        String input = "100.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        //act
        double result = InputValidator.getValidUpdatedMonthlySalary("String\n", scanner);

        //assert
        assertTrue(outContent.toString().contains("Invalid input"));
        assertEquals(100.0, result);
    }

    @Test
    public void testNegativeNumberAndNegativeScanerUpdatedSalary() {
        //arrange
        String input = "-100.0\n300.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        //act
        double result = InputValidator.getValidUpdatedMonthlySalary("-200.0", scanner);

        //assert
        assertTrue(outContent.toString().contains("cannot be negative"));
        assertEquals(300.0, result);
    }

    @Test
    public void testInvalidNumberAndInvalidScanerUpdatedSalary() {
        //arrange
        String input = "String 10.0\n200.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        //act
        double result = InputValidator.getValidUpdatedMonthlySalary("String 100.0", scanner);

        //assert
        assertTrue(outContent.toString().contains("Invalid input"));
        assertEquals(200.0, result);
    }

    @Test
    public void testIntNumberUpdatedSalary() {
        //arrange
        String input = "200.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        //act
        double result = InputValidator.getValidUpdatedMonthlySalary("100", scanner);

        //assert
        assertEquals(100.0, result);
    }

    @Test
    public void testValidInputUpdatedString() {
        //arrange
        String input = "String\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        //act
        String result = InputValidator.getValidUpdatedString("String", scanner);

        //assert
        assertEquals("String", result);
    }

    @Test
    public void testInvalidParameterAndValidScannerUpdatedString() {
        //arrange
        String input = "String\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        //act
        String result = InputValidator.getValidUpdatedString("12String", scanner);

        //assert
        assertTrue(outContent.toString().contains("Invalid input"));
        assertEquals("String", result);
    }

    @Test
    public void testValidParameterAndInvalidScannerUpdatedString() {
        //arrange
        String input = "12String\nString\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        //act
        String result = InputValidator.getValidUpdatedString("String", scanner);

        //assert
        assertEquals("String", result);
    }

    @Test
    public void testInvalidParameterAndInvalidScannerUpdatedString() {
        //arrange
        String input = "12String\nString\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        System.setOut(new PrintStream(outContent));

        //act
        String result = InputValidator.getValidUpdatedString("S123tring", scanner);

        //assert
        assertTrue(outContent.toString().contains("Invalid input"));
        assertEquals("String", result);
    }

    @Test
    public void testRightTextFromParameterEditingUpdatedString() {
        //arrange
        String input = "Scaner\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        //act
        String result = InputValidator.getValidUpdatedString("strINg", scanner);

        //assert
        assertEquals("String", result);
    }

    @Test
    public void testRightTextFromScanerEditingUpdatedString() {
        //arrange
        String input = "sCaNEr\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        //act
        String result = InputValidator.getValidUpdatedString("strINg?", scanner);

        //assert
        assertEquals("Scaner", result);
    }

}
