package synergyhubback.attendance.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import synergyhubback.attendance.domain.entity.DayOff;
import synergyhubback.employee.domain.entity.Employee;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
public class DayOffResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int doCode;                                                      //휴가코드(pk)

    private String doName;                                                   //휴가명

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate doStartDate;                                           //시작일자

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate doEndDate;                                             //종료일자

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime doStartTime;                                           //시작시간

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime doEndTime;                                             //종료시간

    public DayOffResponse(DayOff dayOff) {
        this.doCode = dayOff.getDoCode();
        this.doName = dayOff.getDoName();
        this.doStartDate = dayOff.getDoStartDate();
        this.doEndDate = dayOff.getDoEndDate();
        this.doStartTime = dayOff.getDoStartTime();
        this.doEndTime = dayOff.getDoEndTime();
    }

}
