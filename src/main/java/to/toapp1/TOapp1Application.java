package to.toapp1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import javax.xml.validation.Validator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

@SpringBootApplication
public class TOapp1Application {

    public static void main(String[] args) {
        SpringApplication.run(TOapp1Application.class, args);
    }

    @Bean
    @Profile("!test")
    public CommandLineRunner commandLineRunner (WorkerService workerService){

        return args -> {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Enter: \n '1' to add a new worker" +
                                   "\n '2' to delete all workers with the same surname" +
                                   "\n '3' to delete worker by ID" +
                                   "\n '4' to show all workers" +
                                   "\n '5' to show worker by ID" +
                                   "\n '6' to find all workers by salary range" +
                                   "\n '7' to update worker by ID" +
                                   "\n '8' to find worker by position" +
                                   "\n '9' to delete all workers" +
                                   "\n '10' to delete worker by position" +
                                   "\n '0' to stop:");
                String userInput = scanner.nextLine();
                System.out.print("\033[H\033[2J");
                System.out.flush();
                switch (userInput) {
                    case "0" -> {
                        return;
                    }
                    case "1" -> addWorkerFromTerminal(workerService, scanner);
                    case "2" -> deleteWorkersBySurnameFromTerminal(workerService, scanner);
                    case "3" -> deleteWorkerByID(workerService, scanner);
                    case "4" -> showAllWorkersInTerminal(workerService);
                    case "5" -> showWorkerByID(workerService, scanner);
                    case "6" -> showWorkersBySalaryRangeInTerminal(workerService, scanner);
                    case "7" -> updatedWorkerByID(workerService, scanner);
                    case "8" -> showWorkerByPosition(workerService, scanner);
                    case "9" -> deleteAllWorkers(workerService);
                    case "10" -> deleteWorkersByPosition(workerService, scanner);
                    default -> System.out.println("Invalid input. Please try again.");
                }
            }
        };
    }

    void addWorkerFromTerminal(WorkerService workerService, Scanner scanner){
        System.out.println("Enter worker's name:");
        String name = InputValidator.getValidString(scanner);

        System.out.println("Enter worker's surname:");
        String surname = InputValidator.getValidString(scanner);

        System.out.println("Enter worker's monthly salary:");
        double monthly_salary = InputValidator.getValidMonthlySalary(scanner);

        System.out.println("Enter worker's position:");
        String position = InputValidator.getValidString(scanner);

        WorkerEntity newWorker = new WorkerEntity(name, surname, monthly_salary, position);
        workerService.addWorker(newWorker);
    }

    void deleteWorkersBySurnameFromTerminal(WorkerService workerService, Scanner scanner){
        System.out.println("Enter surname:");
        String surname = scanner.nextLine();
        workerService.deleteAllWorkersBySurname(surname);
    }

    void showAllWorkersInTerminal(WorkerService workerService){
        List<WorkerEntity> allWorkers = workerService.showAllWorkers();
        if(allWorkers.isEmpty()){
            System.out.println("No workers here.");
        }
        else {
            System.out.println("Workers:");
            for(WorkerEntity worker : allWorkers){
                System.out.println(worker.toString());
            }
        }
    }

    void showWorkersBySalaryRangeInTerminal(WorkerService workerService, Scanner scanner){
        System.out.println("Enter worker's min monthly salary:");
        double min = InputValidator.getValidMonthlySalary((scanner));
        System.out.println("Enter worker's max monthly salary:");
        double max = InputValidator.getValidMonthlySalary((scanner));
        if(min >= max) {
            System.out.println("Min monthly salary has to be lower than max monthly salary.");
        }
        List<WorkerEntity> workers = workerService.findBySalaryBetween(min, max);
        if(workers.isEmpty()){
            System.out.println("No workers here.");
        }
        else {
            System.out.println("Workers:");
            for(WorkerEntity worker : workers){
                System.out.println(worker.toString());
            }
        }
    }

    void updatedWorkerByID(WorkerService workerService, Scanner scanner) {
        System.out.println("Enter ID of worker whom you want to update: (if you don't want to change it, just click ENTER)");
        Long id = InputValidator.getValidId(scanner);

        String name;
        String surname;
        Double monthlySalary;
        String position;

        try {
            WorkerEntity workerToEdit = workerService.getByID(id);
            System.out.println("\nWorker with chosen ID:\n");
            System.out.println(workerToEdit.toString());

            System.out.println("If you want to change worker's name, insert it:");
            name = scanner.nextLine();
            if (name.isEmpty()) {
                name = workerToEdit.getName();
            } else {
                name = InputValidator.getValidUpdatedString(name, scanner);
            }

            System.out.println("If you want to change worker's surname, insert it:");
            surname = scanner.nextLine();
            if (surname.isEmpty()) {
                surname = workerToEdit.getSurname();
            } else {
                surname = InputValidator.getValidUpdatedString(surname, scanner);
            }

            System.out.println("If you want to change worker's monthly salary, insert it:");
            String strMonthlySalary = scanner.nextLine();
            if (strMonthlySalary.isEmpty()) {
                monthlySalary = workerToEdit.getMonthlySalary();
            } else {
                monthlySalary = InputValidator.getValidUpdatedMonthlySalary(strMonthlySalary, scanner);
            }

            System.out.println("If you want to change worker's position, insert it:");
            position = scanner.nextLine();
            if (position.isEmpty()) {
                position = workerToEdit.getPosition();
            } else {
                position = InputValidator.getValidUpdatedString(position, scanner);
            }

            workerService.updateWorker(id, name, surname, monthlySalary, position);

        } catch (NoSuchElementException e) {
            System.out.println("No worked with that ID.");
            return;
        }
    }

    void deleteWorkerByID(WorkerService workerService, Scanner scanner) {
        System.out.println("Enter ID of worker that you want to delete:");
        Long id = InputValidator.getValidId(scanner);

        try {
            workerService.deleteWorkerByID(id);
        } catch (NoSuchElementException e) {
            System.out.println("No worked with that ID.");
            return;
        }
    }

    void showWorkerByID(WorkerService workerService, Scanner scanner) {
        System.out.println("Enter ID of worker that you want to show:");
        Long id = InputValidator.getValidId(scanner);

        try {
            WorkerEntity worker = workerService.getByID(id);
            System.out.println("\nThe chosen worker\n");
            System.out.println(worker.toString());
        } catch (NoSuchElementException e) {
            System.out.println("No worked with that ID.");
            return;
        }

    }

    void showWorkerByPosition(WorkerService workerService, Scanner scanner) {
        System.out.println("Enter worker's position:");
        String position = InputValidator.getValidString(scanner);

        List<WorkerEntity> workers = workerService.findWorkerByPosition(position);

        if(workers.isEmpty()){
            System.out.println("No workers here.");
        }
        else {
            System.out.println("Workers with this position:");
            for(WorkerEntity worker : workers){
                System.out.println(worker.toString());
            }
        }
    }

    void deleteAllWorkers(WorkerService workerService) {
        workerService.deleteAllWorkers();
        System.out.println("\nALL WORKERS DELETED!\n");
    }

    void deleteWorkersByPosition(WorkerService workerService, Scanner scanner) {
        System.out.println("Enter worker's position:");
        String position = InputValidator.getValidString(scanner);
        workerService.deleteWorkersByPosition(position);
        System.out.println("\nWORKERS WITH THIS POSITION DELETED\n");
    }
}
