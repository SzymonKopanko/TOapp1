package to.toapp1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class TOapp1Application {

    public static void main(String[] args) {
        SpringApplication.run(TOapp1Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner (WorkerService workerService){
        return args -> {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Enter '1' to add a new worker, '2' to delete by surname," +
                        "'3' to show all workers, '0' to stop:");
                String userInput = scanner.nextLine();
                System.out.print("\033[H\033[2J");
                System.out.flush();
                switch (userInput) {
                    case "0" -> {
                        return;
                    }
                    case "1" -> addWorkerFromTerminal(workerService, scanner);
                    case "2" -> deleteWorkersBySurnameFromTerminal(workerService, scanner);
                    case "3" -> showAllWorkersInTerminal(workerService);
                    default -> System.out.println("Invalid input. Please try again.");
                }
            }
        };
    }

    void addWorkerFromTerminal(WorkerService workerService, Scanner scanner){
        System.out.println("Enter worker's name:");
        String name = scanner.nextLine();

        System.out.println("Enter worker's surname:");
        String surname = scanner.nextLine();

        System.out.println("Enter worker's monthly salary:");
        double monthly_salary = Double.parseDouble(scanner.nextLine());

        System.out.println("Enter worker's position:");
        String position = scanner.nextLine();
        WorkerEntity newWorker = new WorkerEntity(name, surname, monthly_salary, position);
        workerService.addWorker(newWorker);
    }
    void deleteWorkersBySurnameFromTerminal(WorkerService workerService, Scanner scanner){
        System.out.println("Enter surnname:");
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
}
