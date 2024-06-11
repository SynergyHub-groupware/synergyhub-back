package synergyhubback.employee.domain.entity;


import lombok.Getter;
import java.util.Date;

@Getter
public class Employee {

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

    public Employee(int emp_code, String emp_name, int emp_pass, String social_security_no, String email, String phone, String address, int direct_line, String account_num, Date hire_date, Date end_date, String emp_status, String emp_img, String dept_code, String title_code, String position_code, String bank_code) {
        this.emp_code = emp_code;
        this.emp_name = emp_name;
        this.emp_pass = emp_pass;
        this.social_security_no = social_security_no;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.direct_line = direct_line;
        this.account_num = account_num;
        this.hire_date = hire_date;
        this.end_date = end_date;
        this.emp_status = emp_status;
        this.emp_img = emp_img;
        this.dept_code = dept_code;
        this.title_code = title_code;
        this.position_code = position_code;
        this.bank_code = bank_code;
    }
}
