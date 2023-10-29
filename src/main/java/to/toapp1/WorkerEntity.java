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
    private Double monthlySalary;
    private String position;
    private LocalDateTime startOfEmployment;

    public WorkerEntity(String name, String surname, Double monthlySalary, String position){
        this.name = name;
        this.surname = surname;
        this.monthlySalary = monthlySalary;
        this.position = position;
        this.startOfEmployment = LocalDateTime.now();
    }

    public WorkerEntity() {
        this.startOfEmployment = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "id: " + id + '\n' +
                "name: " + name + '\n' +
                "surname: " + surname + '\n' +
                "monthly salary: " + monthlySalary + '\n' +
                "position: " + position + '\n' +
                "start of employment: " + startOfEmployment.toLocalDate() + '\n';
    }

    public void setName(String name) {
        this.name = name;    
    }

    public void setSurname(String surname) {
        this.surname = surname;    
    }

    public void setMonthlySalary(Double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
