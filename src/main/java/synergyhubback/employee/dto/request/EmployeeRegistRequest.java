package synergyhubback.employee.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class EmployeeRegistRequest {

    private final int emp_code;

    @NotBlank
    private final String emp_name;

    private final String emp_pass;

    @NotBlank
    private final String social_security_no;

    @NotBlank
    private final String dept_code;

    @NotBlank
    private final String position_code;

    @NotBlank
    private final String title_code;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate hire_date;

    private final String emp_status;

}
