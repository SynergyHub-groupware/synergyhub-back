package synergyhubback.employee.domain.entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;


@Entity
@Table(name = "employee_info")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Employee {

    @Id
    private int emp_code;
    private String emp_name;
    private String emp_pass;
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
    private String refreshToken;    // 생성함



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_code", referencedColumnName = "dept_code")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title_code", referencedColumnName = "title_code")
    private Title title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_code", referencedColumnName = "position_code")
    private Position position;

    private int bank_code;

    public Employee(int emp_code, String emp_name, String emp_pass, String social_security_no, LocalDate hire_date, String emp_status, String dept_code, String title_code, String position_code) {
        this.emp_code = emp_code;
        this.emp_name = emp_name;
        this.emp_pass = emp_pass;
        this.social_security_no = social_security_no;
        this.hire_date = hire_date;
        this.emp_status = emp_status;
    }

    public static Employee regist(int emp_code, String emp_name, String emp_pass, String social_security_no, String dept_code, String position_code, String title_code, LocalDate hire_date, String emp_status) {

        return new Employee(
                emp_code,
                emp_name,
                emp_pass,
                social_security_no,
                hire_date,
                emp_status,
                dept_code,
                title_code,
                position_code
        );
    }

    // 이재현 로그인 관련 employee entity 로직 생성
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        System.out.println("updateRefreshToken");
    }
}
