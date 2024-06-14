package synergyhubback.calendar.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EMPLOYEE_INFO")
@Getter
@Setter
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMP_CODE")
    private Long id;

    @Column(name = "EMP_NAME")
    private String name;

    // 다른 컬럼들도 필요하면 추가합니다
}
