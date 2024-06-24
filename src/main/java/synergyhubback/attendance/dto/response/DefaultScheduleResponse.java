package synergyhubback.attendance.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synergyhubback.attendance.domain.entity.DefaultSchedule;
import synergyhubback.employee.domain.entity.Employee;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor
public class DefaultScheduleResponse {

    private int dsCode;

    private String deptTitle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime atdStartTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime atdEndTime;

    @Column(name = "emp_code")
    private Integer empCode;

    public DefaultScheduleResponse(DefaultSchedule defaultSchedule) {
        this.dsCode = defaultSchedule.getDsCode();
        this.deptTitle = defaultSchedule.getDeptTitle();
        this.atdStartTime = defaultSchedule.getAtdStartTime();
        this.atdEndTime = defaultSchedule.getAtdEndTime();

        // Null-safe access using Optional
        Optional<Employee> optionalEmployee = Optional.ofNullable(defaultSchedule.getEmployee());
        this.empCode = optionalEmployee.map(Employee::getEmp_code).orElse(null); // 기본값으로 null 설정
    }
}
