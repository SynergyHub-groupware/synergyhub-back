package synergyhubback.attendance.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import synergyhubback.employee.domain.entity.Employee;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendance")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int atdCode;                //근태코드(pk)
    private LocalDate atdDate;          //근무날짜
    private LocalTime atdStartTime;     //지정출근시간
    private LocalTime atdEndTime;       //지정퇴근시간
    private LocalTime startTime;        //출근시간
    private LocalTime endTime;          //퇴근시간

    @ManyToOne
    @JoinColumn(name = "emp_Code")
    private Employee employee;          //사원코드

    private int atsCode;                //근무상태코드 (추후 fk)


    @Builder
    public Attendance(int atdCode, LocalDate atdDate, LocalTime atdStartTime, LocalTime atdEndTime, LocalTime startTime, LocalTime endTime, Employee employee, int atsCode) {
        this.atdCode = atdCode;
        this.atdDate = atdDate;
        this.atdStartTime = atdStartTime;
        this.atdEndTime = atdEndTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.employee = employee;
        this.atsCode = atsCode;
    }

    // 출근시간 업데이트
    public void updateStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    // 퇴근시간 업데이트
    public void updateEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
