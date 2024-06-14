package synergyhubback.employee.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import synergyhubback.employee.domain.entity.Department;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class DeptDetailResponse {

    private String dept_code;

    private String dept_manager_name;

    private String dept_title;

    private List<String> sup_dept_title;

    private List<String> sub_dept_title;

    private int numOfTeamMember;

    private List<String> teamMemberName;

    private LocalDate creation_date;

    private LocalDate end_date;


    public static DeptDetailResponse getDeptDetail(Department department, String deptManagerName, List<String> supDeptTitles, List<String> subDeptTitles, int numOfTeamMember, List<String> teamMemberName) {

        return new DeptDetailResponse(

                department.getDept_code(),
                deptManagerName,
                department.getDept_title(),
                supDeptTitles,
                subDeptTitles,
                numOfTeamMember,
                teamMemberName,
                department.getCreation_date(),
                department.getEnd_date()

        );
    }
}
