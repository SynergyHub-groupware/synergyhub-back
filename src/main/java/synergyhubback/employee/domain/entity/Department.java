package synergyhubback.employee.domain.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "department")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Department {

    @Id
    private String dept_code;

    private String dept_title;

    private LocalDate creation_date;

    private LocalDate end_date;

    public Department(String dept_code, String dept_title, LocalDate creation_date, LocalDate end_date) {
        this.dept_code = dept_code;
        this.dept_title = dept_title;
        this.creation_date = creation_date;
        this.end_date = end_date;
    }
}
