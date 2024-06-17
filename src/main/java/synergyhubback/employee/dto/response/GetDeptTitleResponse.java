package synergyhubback.employee.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import synergyhubback.employee.domain.entity.Department;
import synergyhubback.employee.domain.entity.Employee;

@Getter
@Setter
@AllArgsConstructor
public class GetDeptTitleResponse {

    private String dept_code;

    private String dept_title;

    public static GetDeptTitleResponse getDeptTitle(Department department) {

        return new GetDeptTitleResponse(

                department.getDept_code(),
                department.getDept_title()

        );
    }
}
