package synergyhubback.employee.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import synergyhubback.employee.domain.entity.Employee;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeResponse {

    private int emp_code;
    private String emp_name;
    private int emp_pass;
    private String social_security_no;
    private String email;
    private String phone;
    private String address;
    private int direct_line;
    private String account_num;
    private Date hire_date;
    private Date end_date;
    private String emp_status;
    private String emp_img;
    private String dept_code;
    private String title_code;
    private String position_code;
    private String bank_code;

    public static EmployeeResponse from(Employee employee) {

        return new EmployeeResponse(
                employee.getEmp_code(),
                employee.getEmp_name(),
                employee.getEmp_pass(),
                employee.getSocial_security_no(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getAddress(),
                employee.getDirect_line(),
                employee.getAccount_num(),
                employee.getHire_date(),
                employee.getEnd_date(),
                employee.getEmp_status(),
                employee.getEmp_img(),
                employee.getDept_code(),
                employee.getTitle_code(),
                employee.getPosition_code(),
                employee.getBank_code()
        );
    }
}
