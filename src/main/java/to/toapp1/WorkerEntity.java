package to.toapp1;

import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "workers")
public class WorkerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String  surname;
    private double monthly_salary;
    private String position;
    private LocalDateTime start_of_employment;

    public WorkerEntity(String name, String surname, double monthly_salary, String position){
        this.name = name;
        this.surname = surname;
        this.monthly_salary = monthly_salary;
        this.position = position;
        this.start_of_employment = LocalDateTime.now();
    }


    public WorkerEntity() {
        this.start_of_employment = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "id: " + id + '\n' +
                "name: " + name + '\n' +
                "surname: " + surname + '\n' +
                "monthly salary: " + monthly_salary + '\n' +
                "position: " + position + '\n' +
                "start of employment: " + start_of_employment.toLocalDate() + '\n';
    }
}
