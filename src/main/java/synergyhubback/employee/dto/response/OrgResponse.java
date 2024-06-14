package synergyhubback.employee.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import synergyhubback.employee.domain.entity.Employee;

@Getter
@Setter
@AllArgsConstructor
public class OrgResponse {

    private String emp_img;
    private String emp_name;
    private String dept_code;
    private String position_code;
    private String title_code;

    public static OrgResponse getOrg(Employee employee) {

        return new OrgResponse(
                employee.getEmp_img(),
                employee.getEmp_name(),
                employee.getDepartment().getDept_title(),
                employee.getPosition().getPosition_name(),
                employee.getTitle().getTitle_name()
        );
    }
}
