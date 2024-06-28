package synergyhubback.employee.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DetailByEmpRegistRequest {

    private String erd_num;

    private String erd_title;

    private int emp_code;

}
