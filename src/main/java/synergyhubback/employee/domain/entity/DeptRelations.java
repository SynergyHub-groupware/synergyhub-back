package synergyhubback.employee.domain.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "dept_relations")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DeptRelations {

    @Id
    private int dept_relations_code;

    private String sup_dept_code;

    private String sub_dept_code;

}
