package synergyhubback.attendance.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import synergyhubback.employee.domain.entity.Employee;

import java.time.LocalTime;

@Entity
@Table(name = "DEFAULT_SCHEDULE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class DefaultSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ds_code")
    private int dsCode;

    @Column(name = "dept_code")
    private String deptCode;

    @Column(name = "atd_start_time")
    private LocalTime atdStartTime;

    @Column(name = "atd_end_time")
    private LocalTime atdEndTime;

    @ManyToOne
    @JoinColumn(name = "emp_Code")
    private Employee employee;          //사원코드

    @Builder
    public DefaultSchedule(int dsCode, String deptCode, LocalTime atdStartTime, LocalTime atdEndTime, Employee employee) {
        this.dsCode = dsCode;
        this.deptCode = deptCode;
        this.atdStartTime = atdStartTime;
        this.atdEndTime = atdEndTime;
        this.employee = employee;
    }

    /* 지정 출퇴근시간 업데이트 */
    public void updateStartTime(LocalTime atdStartTime) {
        this.atdStartTime = atdStartTime;
    }

    public void updateEndTime(LocalTime atdEndTime) {
        this.atdEndTime = atdEndTime;
    }
}
