package synergyhubback.attendance.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import synergyhubback.attendance.domain.entity.DayOff;
import synergyhubback.attendance.domain.entity.DayOffBalance;
import synergyhubback.employee.domain.entity.Employee;

import java.time.LocalDate;

@Getter
@Setter
public class DayOffBalanceResponse {

    private int dbCode;                 //휴가관리코드(pk)

    private int granted;                //부여수

    private int remaining;              //잔여수

    private int dbUsed;                 //사용수

    private Employee employee;          //사원코드

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dbInsertDate;     //부여일자

    public DayOffBalanceResponse(DayOffBalance dayOffBalance) {
        this.dbCode = dayOffBalance.getDbCode();
        this.granted = dayOffBalance.getGranted();
        this.remaining = dayOffBalance.getRemaining();
        this.dbUsed = dayOffBalance.getDbUsed();
        this.employee = dayOffBalance.getEmployee();
        this.dbInsertDate = dayOffBalance.getDbInsertDate();
    }

}
