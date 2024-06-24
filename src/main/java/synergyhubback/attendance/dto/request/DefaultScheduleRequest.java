package synergyhubback.attendance.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synergyhubback.employee.domain.entity.Employee;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@NoArgsConstructor
public class DefaultScheduleRequest {

    private String deptTitle;
    private String atdStartTime;
    private String atdEndTime;
    private Employee employee;

    @JsonCreator
    public DefaultScheduleRequest(@JsonProperty("deptTitle") String deptTitle,
                                  @JsonProperty("atdStartTime") String atdStartTime,
                                  @JsonProperty("atdEndTime") String atdEndTime,
                                  @JsonProperty("employee") Employee employee) {
        this.deptTitle = deptTitle;
        this.atdStartTime = atdStartTime;
        this.atdEndTime = atdEndTime;
        this.employee = employee;
    }

    // LocalTime으로 파싱된 값을 가져오는 메서드
    public LocalTime getParsedStartTime() {
        return LocalTime.parse(atdStartTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    // LocalTime으로 파싱된 값을 가져오는 메서드
    public LocalTime getParsedEndTime() {
        return LocalTime.parse(atdEndTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
