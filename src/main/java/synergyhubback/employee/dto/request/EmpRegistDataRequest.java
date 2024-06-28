package synergyhubback.employee.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class EmpRegistDataRequest {

    private String detailErdNum;
    private String detailErdTitle;
    private List<EmployeeRegistRequest> employees;

}
