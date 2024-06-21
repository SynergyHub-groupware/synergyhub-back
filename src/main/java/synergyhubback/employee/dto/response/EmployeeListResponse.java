package synergyhubback.employee.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import synergyhubback.employee.domain.entity.Employee;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
public class EmployeeListResponse {

    private List<EmployeeResponse> employees;

    public static EmployeeListResponse getEmployeeList(List<Employee> employees) {

        List<EmployeeResponse> employeeResponses = employees.stream()
                .map(employee -> new EmployeeResponse(
                        employee.getEmp_code(),
                        employee.getEmp_name(),
                        employee.getDepartment().getDept_title(),
                        employee.getPosition().getPosition_name(),
                        employee.getPhone(),
                        employee.getHire_date(),
                        employee.getSocial_security_no()
                ))
                .collect(Collectors.toList());
        return new EmployeeListResponse(employeeResponses);
    }

    
}


