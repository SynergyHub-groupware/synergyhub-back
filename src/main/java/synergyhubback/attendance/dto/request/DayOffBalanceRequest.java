package synergyhubback.attendance.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synergyhubback.employee.domain.entity.Employee;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class DayOffBalanceRequest {

    private int dbCode;                 //휴가관리코드(pk)
    private int granted;                //부여수
    private int remainnig;              //잔여수
    private int dbUsed;                 //사용수
    private Employee employee;          //사원코드
    private LocalDate dbInsertDate;     //부여일자

    @JsonCreator
    public DayOffBalanceRequest(@JsonProperty("dbCode") int dbCode,
                                 @JsonProperty("granted") int granted,
                                 @JsonProperty("remainnig") int remainnig,
                                 @JsonProperty("dbUsed") int dbUsed,
                                 @JsonProperty("employee") Employee employee,
                                 @JsonProperty("dbInsertDate") LocalDate dbInsertDate) {
        this.dbCode = dbCode;
        this.granted = granted;
        this.remainnig = remainnig;
        this.dbUsed = dbUsed;
        this.employee = employee;
        this.dbInsertDate = dbInsertDate;
    }



}
