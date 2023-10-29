package to.toapp1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WorkerRepository extends JpaRepository<WorkerEntity, Long> {
    List<WorkerEntity> findByName(String name);
    List<WorkerEntity> findBySurname(String surname);
    List<WorkerEntity> findByPosition(String position);

}
