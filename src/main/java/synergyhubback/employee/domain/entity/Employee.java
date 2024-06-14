package synergyhubback.employee.domain.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import synergyhubback.attendance.domain.entity.Attendance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee_info")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Employee {

    @Id
    private int emp_code;
    private String emp_name;
    private int emp_pass;
    private String social_security_no;
    private String email;
    private String phone;
    private String address;
    private int direct_line;
    private String account_num;
    private LocalDate hire_date;
    private LocalDate end_date;
    private String emp_status;
    private String emp_sign;
    private String emp_img;
    private String dept_code;
    private String title_code;
    private String position_code;
    private int bank_code;

    public Employee(int emp_code, String emp_name, int emp_pass, String social_security_no, LocalDate hire_date, String emp_status, String dept_code, String title_code, String position_code) {
        this.emp_code = emp_code;
        this.emp_name = emp_name;
        this.emp_pass = emp_pass;
        this.social_security_no = social_security_no;
        this.hire_date = hire_date;
        this.emp_status = emp_status;
        this.dept_code = dept_code;
        this.title_code = title_code;
        this.position_code = position_code;
    }

}
