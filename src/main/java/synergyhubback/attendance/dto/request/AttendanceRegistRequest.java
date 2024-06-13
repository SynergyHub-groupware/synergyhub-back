package synergyhubback.attendance.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AttendanceRegistRequest {

    private int atdCode;                //근태코드(pk)
    private LocalDate atdDate;          //근무날짜
    private LocalTime atdStartTime;     //지정출근시간
    private LocalTime atdEndTime;       //지정퇴근시간
    private LocalTime startTime;        //출근시간
    private LocalTime endTime;          //퇴근시간
    private int empCode;                //사원코드 (추후 fk)
    private int atsCode;                //근무상태코드 (추후 fk)

}
