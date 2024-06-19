package synergyhubback.common.address.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import synergyhubback.employee.domain.entity.Department;
import synergyhubback.employee.domain.entity.Employee;
import synergyhubback.employee.domain.entity.Position;

@Getter
@Setter
@Slf4j
@AllArgsConstructor
public class AddressSelect {

    private String emp_name;
    private String dept_title;
    private String position_name;


    public static AddressSelect getAddressSelect(Employee employee, Department department, Position position) {

        String deptTitle = (department != null) ? department.getDept_title() : "";
        String positionName = (position != null) ? position.getPosition_name() : "";

        return new AddressSelect(employee.getEmp_name(), deptTitle, positionName);
    }
}
