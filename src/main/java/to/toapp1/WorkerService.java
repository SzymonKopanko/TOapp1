package to.toapp1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WorkerService {
    private final WorkerRepository workerRepository;

    @Autowired
    public WorkerService(WorkerRepository workerRepository){
        this.workerRepository = workerRepository;
    }

    public WorkerEntity addWorker(WorkerEntity workerEntity){
        return workerRepository.save(workerEntity);
    }

    public List<WorkerEntity> deleteAllWorkersBySurname(String surname){
        List<WorkerEntity> workersToDelete = workerRepository.findBySurname(surname);
        workerRepository.deleteAll(workersToDelete);
        return workersToDelete;
    }

    public List<WorkerEntity> showAllWorkers(){
        return workerRepository.findAll();
    }

    public List<WorkerEntity> findBySalaryBetween(double min, double max){
        return workerRepository.findByMonthlySalaryBetween(min, max);
    }
}
