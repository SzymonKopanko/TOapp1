package to.toapp1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TOapp1ApplicationTests {

    @Mock
    private WorkerService workerService;

    @InjectMocks
    private TOapp1Application toapp1Application;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor)); // Redirect System.out to capture output
    }

    @Test
    public void testAddWorkerFromTerminal() {
        // Arrange
        String input = "John\nDoe\n5000\nDeveloper\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        // Act
        toapp1Application.addWorkerFromTerminal(workerService, scanner);

        // Assert
        verify(workerService).addWorker(any(WorkerEntity.class));

        // Check if the output is correct
        assertEquals("Enter worker's name:\nEnter worker's surname:\nEnter worker's monthly salary:\nEnter worker's position:", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testDeleteWorkersBySurnameFromTerminal() {
        // Arrange
        String input = "Doe\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        // Act
        toapp1Application.deleteWorkersBySurnameFromTerminal(workerService, scanner);

        // Assert
        verify(workerService).deleteAllWorkersBySurname("Doe");

        // Check if the output is correct
        assertEquals("Enter surname:", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testShowAllWorkersInTerminal() {
        // Arrange
        when(workerService.showAllWorkers()).thenReturn(Collections.emptyList());

        // Act
        toapp1Application.showAllWorkersInTerminal(workerService);

        // Assert
        verify(workerService).showAllWorkers();

        // Check if the output is correct
        assertEquals("No workers here.", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testShowWorkersBySalaryRangeInTerminal(){
        // Arrange
        String input = "1000\n5000\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        when(workerService.findBySalaryBetween(1000, 5000)).thenReturn(Collections.emptyList());

        // Act
        toapp1Application.showWorkersBySalaryRangeInTerminal(workerService, scanner);

        // Assert
        verify(workerService).findBySalaryBetween(1000, 5000);

        // Check if the output is correct
        assertEquals("Enter worker's min monthly salary:\nEnter worker's max monthly salary:\nNo workers here.", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testUpdatedWorkerByID() {
        // Arrange
        WorkerEntity mockWorker = new WorkerEntity("John", "Doe", 5000.0, "Developer");
        when(workerService.getByID(anyLong())).thenReturn(mockWorker);

        String input = "1\nJane\nDoe\n6000\nManager\n"; // Simulate user input to update worker
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        // Act
        toapp1Application.updatedWorkerByID(workerService, scanner);

        // Assert
        verify(workerService).updateWorker(anyLong(), eq("Jane"), eq("Doe"), eq(6000.0), eq("Manager"));
    }

    @Test
    public void testUpdatedWorkerByIDWithNoSuchElementException() {
        // Arrange
        when(workerService.getByID(anyLong())).thenThrow(new NoSuchElementException());

        String input = "1\n"; // Only ID is needed as it will throw an exception
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        // Act
        toapp1Application.updatedWorkerByID(workerService, scanner);

        // Assert
        String expectedOutput = "No worked with that ID.";
        verify(workerService, never()).updateWorker(anyLong(), anyString(), anyString(), anyDouble(), anyString());
        assert(outputStreamCaptor.toString().trim().contains(expectedOutput));
    }

    @Test
    public void testShowWorkerByID() {
        // Arrange
        WorkerEntity mockWorker = new WorkerEntity("John", "Doe", 5000.0, "Developer");
        when(workerService.getByID(anyLong())).thenReturn(mockWorker);
        String input = "123\n"; // ID to show
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        // Act
        toapp1Application.showWorkerByID(workerService, scanner);

        // Assert
        verify(workerService).getByID(anyLong()); // Verify that the service method was called
        Assertions.assertTrue(outputStreamCaptor.toString().contains(mockWorker.toString())); // Verify the correct output
    }

    @Test
    public void testShowWorkerByIDNotFound() {
        // Arrange
        when(workerService.getByID(anyLong())).thenThrow(new NoSuchElementException());
        String input = "123\n"; // ID that does not exist
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        // Act
        toapp1Application.showWorkerByID(workerService, scanner);

        // Assert
        Assertions.assertTrue(outputStreamCaptor.toString().trim().endsWith("No worked with that ID.")); // Verify the correct output
    }

    @Test
    public void testShowWorkerByPosition(){
        // Arrange
        String input = "Developer\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        when(workerService.findWorkerByPosition("Developer")).thenReturn(Collections.emptyList());

        // Act
        toapp1Application.showWorkerByPosition(workerService, scanner);

        // Assert
        verify(workerService).findWorkerByPosition("Developer");

        // Check if the output is correct
        assertEquals("Enter worker's position:\nNo workers here.", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testDeleteAllWorkers() {
        // Arrange
        doNothing().when(workerService).deleteAllWorkers();

        // Act
        toapp1Application.deleteAllWorkers(workerService);

        // Assert
        verify(workerService).deleteAllWorkers(); // Verify that the service method was called
        Assertions.assertTrue(outputStreamCaptor.toString().trim().endsWith("ALL WORKERS DELETED!")); // Verify the correct output
    }

    @Test
    public void testDeleteWorkersByPosition() {
        // Arrange
        doNothing().when(workerService).deleteWorkersByPosition(anyString());
        String input = "Developer\n"; // Position to delete
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        // Act
        toapp1Application.deleteWorkersByPosition(workerService, scanner);

        // Assert
        verify(workerService).deleteWorkersByPosition(anyString()); // Verify that the service method was called
        Assertions.assertTrue(outputStreamCaptor.toString().trim().endsWith("WORKERS WITH THIS POSITION DELETED")); // Verify the correct output
    }


    public void tearDown() {
        System.setOut(System.out);
    }
}
