package synergyhubback.attendance.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class AttendanceRegistStartTimeRequest {

    private LocalTime startTime;

    public AttendanceRegistStartTimeRequest() {
        this.startTime = LocalTime.now();
    }

}
