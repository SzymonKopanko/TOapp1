package to.toapp1;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkerEntityTest {
    @Test
    public void testWorkerEntityConstructorAndGetters() {
        // Arrange
        String name = "John";
        String surname = "Doe";
        Double monthlySalary = 3000.0;
        String position = "Developer";

        // Act
        WorkerEntity worker = new WorkerEntity(name, surname, monthlySalary, position);
        LocalDateTime timeBeforeConstruction = LocalDateTime.now();

        // Assert
        assertEquals(name, worker.getName());
        assertEquals(surname, worker.getSurname());
        assertEquals(monthlySalary, worker.getMonthlySalary());
        assertEquals(position, worker.getPosition());
        assertEquals(timeBeforeConstruction.toLocalDate(), worker.getStartOfEmployment().toLocalDate());
    }

    @Test
    public void testWorkerEntitySetters() {
        // Arrange
        WorkerEntity worker = new WorkerEntity();
        String newName = "Jane";
        String newSurname = "Smith";
        Double newMonthlySalary = 4000.0;
        String newPosition = "Manager";

        // Act
        worker.setName(newName);
        worker.setSurname(newSurname);
        worker.setMonthlySalary(newMonthlySalary);
        worker.setPosition(newPosition);

        // Assert
        assertEquals(newName, worker.getName());
        assertEquals(newSurname, worker.getSurname());
        assertEquals(newMonthlySalary, worker.getMonthlySalary());
        assertEquals(newPosition, worker.getPosition());
    }

    @Test
    public void testToString() {
        // Arrange
        String name = "John";
        String surname = "Doe";
        Double monthlySalary = 3000.0;
        String position = "Developer";
        WorkerEntity worker = new WorkerEntity(name, surname, monthlySalary, position);
        String expectedToString = "id: " + worker.getId() + '\n' +
                "name: " + name + '\n' +
                "surname: " + surname + '\n' +
                "monthly salary: " + monthlySalary + '\n' +
                "position: " + position + '\n' +
                "start of employment: " + worker.getStartOfEmployment().toLocalDate() + '\n';

        // Act
        String actualToString = worker.toString();

        // Assert
        assertEquals(expectedToString, actualToString);
    }
}
