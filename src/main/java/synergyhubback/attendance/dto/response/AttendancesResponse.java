package synergyhubback.attendance.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import synergyhubback.attendance.domain.entity.Attendance;
import synergyhubback.attendance.domain.entity.AttendanceStatus;
import synergyhubback.employee.domain.entity.Employee;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AttendancesResponse {


    private int atdCode;                                                  //근태코드(pk)

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate atdDate;                                            //근무날짜

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime atdStartTime;                                       //지정출근시간

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime atdEndTime;                                         //지정퇴근시간

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime startTime;                                          //출근시간

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime endTime;                                            //퇴근시간

    private Employee employee;                                            //사원코드

    private AttendanceStatus attendanceStatus;                            //근무상태코드

    public AttendancesResponse(Attendance attendance) {
        this.atdCode = attendance.getAtdCode();
        this.atdDate = attendance.getAtdDate();
        this.atdStartTime = attendance.getStartTime();
        this.atdEndTime = attendance.getAtdEndTime();
        this.startTime = attendance.getStartTime();
        this.endTime = attendance.getEndTime();
        this.attendanceStatus = attendance.getAttendanceStatus();
    }

}


