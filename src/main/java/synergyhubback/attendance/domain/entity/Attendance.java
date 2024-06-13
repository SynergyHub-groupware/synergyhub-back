package synergyhubback.attendance.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    private int empCode;                //사원코드 (추후 fk)
    private int atsCode;                //근무상태코드 (추후 fk)


    public Attendance(int newAtdCode, LocalTime startTime) {
        this.atdCode = newAtdCode;
        this.atdDate = LocalDate.now();
        this.startTime = startTime;
        this.atdStartTime = LocalTime.of(9, 0);
        this.atdEndTime = LocalTime.of(18, 0);
        this.empCode = 2024031;
        this.atsCode = 1;
    }

}
