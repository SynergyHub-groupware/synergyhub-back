package synergyhubback.employee.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class EmployeeResponse {

        private int emp_code;
        private String emp_name;
        private String dept_code;
        private String position_code;
        private String phone;
        private LocalDate hire_date;
        private String social_security_no;

}

