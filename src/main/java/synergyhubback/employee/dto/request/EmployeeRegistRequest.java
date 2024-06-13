package synergyhubback.employee.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class EmployeeRegistRequest {

    private final int emp_code;

    @NotBlank
    private final String emp_name;

    private final int emp_pass;

    @NotBlank
    private final String social_security_no;

    @NotBlank
    private final String dept_code;

    @NotBlank
    private final String position_code;

    @NotBlank
    private final String title_code;


    private final LocalDate hire_date;

    private final String emp_status;

}
