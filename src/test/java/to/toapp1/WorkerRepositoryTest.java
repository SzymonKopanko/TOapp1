package to.toapp1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
public class WorkerRepositoryTest {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private TestEntityManager entityManager;

    private WorkerEntity worker;

    @BeforeEach
    void setUp() {
        worker = new WorkerEntity("John", "Doe", 3000.0, "Developer");
        entityManager.persist(worker);
        entityManager.flush();
    }

    @Test
    void addWorkerTest() {
        WorkerEntity worker = new WorkerEntity("John", "Doe", 3000.0, "Developer");
        workerRepository.save(worker);
        assertEquals(2, workerRepository.findAll().size());
    }

    @Test
    void findAllTest() {
        List<WorkerEntity> foundWorkers = workerRepository.findAll();
        assertThat(foundWorkers).hasSize(1);
        assertThat(foundWorkers.get(0)).isEqualTo(worker);
    }

    @Test
    void findByNameTest() {
        List<WorkerEntity> foundWorkers = workerRepository.findByName(worker.getName());
        assertThat(foundWorkers).hasSize(1);
        assertThat(foundWorkers.get(0)).isEqualTo(worker);
    }

    @Test
    void findBySurnameTest() {
        List<WorkerEntity> foundWorkers = workerRepository.findBySurname(worker.getSurname());
        assertThat(foundWorkers).hasSize(1);
        assertThat(foundWorkers.get(0)).isEqualTo(worker);
    }

    @Test
    void findByMonthlySalaryBetweenTest() {
        List<WorkerEntity> foundWorkers = workerRepository.findByMonthlySalaryBetween(2500.0, 3500.0);
        assertThat(foundWorkers).hasSize(1);
        assertThat(foundWorkers.get(0)).isEqualTo(worker);
    }
}
