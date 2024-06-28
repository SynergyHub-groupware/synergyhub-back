package synergyhubback.attendance.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synergyhubback.attendance.domain.entity.DayOff;
import synergyhubback.attendance.domain.entity.DayOffBalance;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@NoArgsConstructor
public class DayOffResponse {

    private int doCode;                                                      //휴가코드(pk)

    private String doName;                                                   //휴가명

    private int doUsed;                                                      //신청일수

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate doStartDate;                                           //시작일자

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate doEndDate;                                             //종료일자

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime doStartTime;                                           //시작시간

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime doEndTime;                                             //종료시간

    private int granted;
    private int remaining;
    private int dbUsed;
    private int empCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dbInsertDate;

    public DayOffResponse(DayOff dayOff) {
        this.doCode = dayOff.getDoCode();
        this.doName = dayOff.getDoName();
        this.doUsed = dayOff.getDoUsed();
        this.doStartDate = dayOff.getDoStartDate();
        this.doEndDate = dayOff.getDoEndDate();
        this.doStartTime = dayOff.getDoStartTime();
        this.doEndTime = dayOff.getDoEndTime();
        this.granted = dayOff.getDayOffBalance().getGranted();
        this.remaining = dayOff.getDayOffBalance().getRemaining();
        this.dbUsed = dayOff.getDayOffBalance().getDbUsed();
        this.empCode = dayOff.getDayOffBalance().getEmployee().getEmp_code();
        this.dbInsertDate = dayOff.getDayOffBalance().getDbInsertDate();
    }

}
