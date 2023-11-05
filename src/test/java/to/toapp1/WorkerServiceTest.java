package to.toapp1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkerServiceTest {

    @Mock
    private WorkerRepository workerRepository;

    @InjectMocks
    private WorkerService workerService;

    private WorkerEntity worker;

    @BeforeEach
    void setUp() {
        worker = new WorkerEntity("John", "Doe", 3000.0, "Developer");
    }

    @Test
    void addWorkerTest() {
        when(workerRepository.save(any(WorkerEntity.class))).thenReturn(worker);
        WorkerEntity savedWorker = workerService.addWorker(worker);
        assertNotNull(savedWorker);
        assertEquals(worker.getName(), savedWorker.getName());
    }

    @Test
    void deleteAllWorkersBySurnameTest() {
        when(workerRepository.findBySurname(worker.getSurname())).thenReturn(Arrays.asList(worker));
        List<WorkerEntity> deletedWorkers = workerService.deleteAllWorkersBySurname(worker.getSurname());
        assertEquals(1, deletedWorkers.size());
        verify(workerRepository, times(1)).deleteAll(deletedWorkers);
    }

    @Test
    void deleteWorkerByIDTest() {
        doNothing().when(workerRepository).deleteById(worker.getId());
        workerService.deleteWorkerByID(worker.getId());
        verify(workerRepository, times(1)).deleteById(worker.getId());
    }

    @Test
    void deleteWorkersByPositionTest() {
        when(workerRepository.findByPosition(worker.getPosition())).thenReturn(Arrays.asList(worker));
        workerService.deleteWorkersByPosition(worker.getPosition());
        verify(workerRepository, times(1)).deleteAll(anyList());
    }

    @Test
    void deleteAllWorkersTest() {
        doNothing().when(workerRepository).deleteAll();
        workerService.deleteAllWorkers();
        verify(workerRepository, times(1)).deleteAll();
    }

    @Test
    void showAllWorkersTest() {
        when(workerRepository.findAll()).thenReturn(Arrays.asList(worker));
        List<WorkerEntity> workers = workerService.showAllWorkers();
        assertEquals(1, workers.size());
        verify(workerRepository, times(1)).findAll();
    }

    @Test
    void findWorkerByPositionTest() {
        when(workerRepository.findByPosition(worker.getPosition())).thenReturn(Arrays.asList(worker));
        List<WorkerEntity> workers = workerService.findWorkerByPosition(worker.getPosition());
        assertEquals(1, workers.size());
        verify(workerRepository, times(1)).findByPosition(worker.getPosition());
    }

    @Test
    void findBySalaryBetweenTest() {
        when(workerRepository.findByMonthlySalaryBetween(anyDouble(), anyDouble())).thenReturn(Arrays.asList(worker));
        List<WorkerEntity> workers = workerService.findBySalaryBetween(2000.0, 4000.0);
        assertEquals(1, workers.size());
        verify(workerRepository, times(1)).findByMonthlySalaryBetween(anyDouble(), anyDouble());
    }

    @Test
    void getByIDTest() {
        when(workerRepository.findById(worker.getId())).thenReturn(Optional.of(worker));
        WorkerEntity foundWorker = workerService.getByID(worker.getId());
        assertNotNull(foundWorker);
        assertEquals(worker.getId(), foundWorker.getId());
    }

    @Test
    void updateWorkerTest() {
        when(workerRepository.save(any(WorkerEntity.class))).thenReturn(worker);
        workerService.updateWorker(worker.getId(), worker.getName(), worker.getSurname(), worker.getMonthlySalary(), worker.getPosition());
        verify(workerRepository, times(1)).save(any(WorkerEntity.class));
    }

}
