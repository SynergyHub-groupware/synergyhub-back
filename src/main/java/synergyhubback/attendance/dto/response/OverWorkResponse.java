package synergyhubback.attendance.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import synergyhubback.attendance.domain.entity.OverWork;
import synergyhubback.employee.domain.entity.Employee;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class OverWorkResponse {

    private int owCode;            //초과근무코드(pk)

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate owDate;       //초과근무 일자

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime startTime;    //시작시간

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime endTime;      //종료시간

    private int employee;           //사원코드 (추후 fk)

    public OverWorkResponse(OverWork overWork) {
        this.owCode = overWork.getOwCode();
        this.owDate = overWork.getOwDate();
        this.startTime = overWork.getStartTime();
        this.endTime = overWork.getEndTime();
        this.employee = overWork.getEmployee().getEmp_code();
    }

}
