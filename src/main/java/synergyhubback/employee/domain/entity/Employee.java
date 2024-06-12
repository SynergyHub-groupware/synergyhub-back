package synergyhubback.employee.domain.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.Date;

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
    private String refreshToken;    // 생성함

    public Employee(int emp_code, String emp_name, int emp_pass, String social_security_no, LocalDate hire_date, String dept_code, String title_code, String position_code) {
        this.emp_code = emp_code;
        this.emp_name = emp_name;
        this.emp_pass = emp_pass;
        this.social_security_no = social_security_no;
        this.hire_date = hire_date;
        this.dept_code = dept_code;
        this.title_code = title_code;
        this.position_code = position_code;
    }

    public static Employee regist(int emp_code, String emp_name, int emp_pass, String social_security_no, String dept_code, String position_code, String title_code, LocalDate hire_date) {

        return new Employee(
                emp_code,
                emp_name,
                emp_pass,
                social_security_no,
                hire_date,
                dept_code,
                title_code,
                position_code
        );
    }

    // 생성함
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
