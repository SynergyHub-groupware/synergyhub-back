package synergyhubback.attendance.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
    private int empCode;                //사원코드
    private int atsCode;                //근무상태코드 (추후 fk)


    @Builder
    public Attendance(int atdCode, LocalDate atdDate, LocalTime atdStartTime, LocalTime atdEndTime, LocalTime startTime, LocalTime endTime, int empCode, int atsCode) {
        this.atdCode = atdCode;
        this.atdDate = atdDate;
        this.atdStartTime = atdStartTime;
        this.atdEndTime = atdEndTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.empCode = empCode;
        this.atsCode = atsCode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Attendance{");
        sb.append("atdCode=").append(atdCode);
        sb.append(", atdDate=").append(atdDate);
        sb.append(", atdStartTime=").append(atdStartTime);
        sb.append(", atdEndTime=").append(atdEndTime);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", empCode=").append(empCode);
        sb.append(", atsCode=").append(atsCode);
        sb.append('}');
        return sb.toString();
    }

}
