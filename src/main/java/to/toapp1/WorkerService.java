package to.toapp1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public void deleteWorkerByID(Long id) {
        workerRepository.deleteById(id);
    }

    public void deleteWorkersByPosition(String position) {
        List<WorkerEntity> workersToDelete = workerRepository.findByPosition(position);
        workerRepository.deleteAll(workersToDelete);
    }

    public void deleteAllWorkers() {
        workerRepository.deleteAll();
    }

    public List<WorkerEntity> showAllWorkers(){
        return workerRepository.findAll();
    }

    public List<WorkerEntity> findWorkerByPosition(String position) {
        return workerRepository.findByPosition(position);
    }

    public List<WorkerEntity> findBySalaryBetween(double min, double max){
        return workerRepository.findByMonthlySalaryBetween(min, max);
    }

    public WorkerEntity getByID(Long id) { return workerRepository.findById(id).get(); }

    public void updateWorker(Long oldID, String name, String surname, Double monthlySalary, String position) {
        
        deleteWorkerByID(oldID);

        WorkerEntity updatedWorker = new WorkerEntity(name, surname, monthlySalary, position);
            
        addWorker(updatedWorker);

    }
}
