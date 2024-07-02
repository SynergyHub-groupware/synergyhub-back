package synergyhubback.attendance.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import synergyhubback.attendance.domain.entity.Attendance;
import synergyhubback.attendance.domain.entity.AttendanceStatus;
import synergyhubback.employee.domain.entity.DeptRelations;
import synergyhubback.employee.domain.entity.Employee;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

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

    private String parTitle;  //상위부서명
    private String subTitle;  //하위부서명
    private String deptTitle; //부서명
    private String empName;   //사원명

    private int empCode;                                                    //사원코드

    private AttendanceStatus attendanceStatus;                            //근무상태코드

    public AttendancesResponse(Attendance attendance) {
        this.atdCode = attendance.getAtdCode();
        this.empCode = attendance.getEmployee().getEmp_code();
        this.empName = attendance.getEmployee().getEmp_name();
        this.deptTitle = attendance.getEmployee().getDepartment().getDept_title();
        this.atdDate = attendance.getAtdDate();
        this.atdStartTime = attendance.getAtdStartTime();
        this.atdEndTime = attendance.getAtdEndTime();
        this.startTime = attendance.getStartTime();
        this.endTime = attendance.getEndTime();
        this.attendanceStatus = attendance.getAttendanceStatus();

        // 상위부서명 가져오기
        StringBuilder parentDeptTitles = new StringBuilder();
        Set<DeptRelations> parentDepartments = attendance.getEmployee().getDepartment().getParentDepartments();
        for (DeptRelations deptRelation : parentDepartments) {
            parentDeptTitles.append(deptRelation.getParentDepartment().getDept_title()).append(", ");
            // 상위부서의 상위부서명 가져오기
            for (DeptRelations grandParentRelation : deptRelation.getParentDepartment().getParentDepartments()) {
                this.parTitle = grandParentRelation.getParentDepartment().getDept_title();
            }
        }
        parentDeptTitles.setLength(parentDeptTitles.length() - 2); // 마지막 쉼표와 공백 제거
        this.subTitle = parentDeptTitles.toString();
    }


}


