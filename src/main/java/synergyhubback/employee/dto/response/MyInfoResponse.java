package synergyhubback.employee.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import synergyhubback.employee.domain.entity.Employee;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class MyInfoResponse {

    private String emp_name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate hire_date;
    private int emp_code;
    private String dept_code;
    private String title_code;
    private String email;
    private String phone;
    private String address;
    private int bank_code;
    private String account_num;

    public static MyInfoResponse getMyInfo(Employee employee) {

        return new MyInfoResponse(
                employee.getEmp_name(),
                employee.getHire_date(),
                employee.getEmp_code(),
                employee.getDepartment().getDept_code(),
                employee.getTitle().getTitle_code(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getAddress(),
                employee.getBank_code(),
                employee.getAccount_num()
        );
    }
}
