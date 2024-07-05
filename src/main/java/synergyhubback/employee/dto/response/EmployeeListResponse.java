    package synergyhubback.employee.dto.response;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import synergyhubback.employee.domain.entity.Employee;

    import java.util.ArrayList;
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

                            employee.getPosition().getPosition_code(),
                            employee.getEmp_code(),
                            employee.getPar_code(),
                            employee.getEmp_name(),
                            employee.getDepartment().getDept_code(),
                            employee.getDepartment().getDept_title(),
                            employee.getPosition().getPosition_name(),
                            employee.getTitle().getTitle_name(),
                            employee.getPhone(),
                            employee.getHire_date(),
                            employee.getEmp_status(),
                            employee.getSocial_security_no(),
                            employee.getEmp_img(),
                            new ArrayList<>()

                    ))
                    .collect(Collectors.toList());
            return new EmployeeListResponse(employeeResponses);
        }
    }


