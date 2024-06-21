package synergyhubback.employee.domain.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "certificate")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cer_code;

    private String cer_name;

    private String cer_score;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate cer_date;

    private String cer_num;

    private String iss_organ;

    private int emp_code;

    public Certificate(int cer_code, String cer_name, String cer_score, LocalDate cer_date, String cer_num, String iss_organ, int emp_code) {
        this.cer_code = cer_code;
        this.cer_name = cer_name;
        this.cer_score = cer_score;
        this.cer_date = cer_date;
        this.cer_num = cer_num;
        this.iss_organ = iss_organ;
        this.emp_code = emp_code;
    }
}
