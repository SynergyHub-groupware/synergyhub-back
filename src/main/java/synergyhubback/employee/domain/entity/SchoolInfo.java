package synergyhubback.employee.domain.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "shcool_info")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SchoolInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sch_code;

    private String sch_name;

    private String grad_status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate enrole_date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate grad_date;

    private String major;

    private String day_n_night;

    private String location;

    private int emp_code;

    public SchoolInfo(int sch_code, String sch_name, String grad_status, LocalDate enrole_date, LocalDate grad_date, String major, String day_n_night, String location, int emp_code) {
        this.sch_code = sch_code;
        this.sch_name = sch_name;
        this.grad_status = grad_status;
        this.enrole_date = enrole_date;
        this.grad_date = grad_date;
        this.major = major;
        this.day_n_night = day_n_night;
        this.location = location;
        this.emp_code = emp_code;
    }
}
