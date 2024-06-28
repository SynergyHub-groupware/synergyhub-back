package synergyhubback.attendance.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import synergyhubback.employee.domain.entity.Employee;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "overwork")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class OverWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ow_code")
    private int owCode;            //초과근무코드(pk)

    @Column(name = "ow_date")
    private LocalDate owDate;       //초과근무 일자

    @Column(name = "start_time")
    private LocalTime startTime;    //시작시간

    @Column(name = "end_time")
    private LocalTime endTime;      //종료시간

    @ManyToOne
    @JoinColumn(name = "emp_Code")
    private Employee employee;           //사원코드 (추후 fk)

}
