package synergyhubback.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import synergyhubback.attendance.domain.entity.Attendance;
import synergyhubback.attendance.domain.entity.AttendanceStatus;
import synergyhubback.attendance.dto.request.AttendanceRegistRequest;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(AttendanceRegistRequest.class, Attendance.class)
                .setProvider(request -> Attendance.builder()
                        .atdCode(((AttendanceRegistRequest) request.getSource()).getAtdCode())
                        .atdDate(((AttendanceRegistRequest) request.getSource()).getAtdDate())
                        .atdStartTime(((AttendanceRegistRequest) request.getSource()).getAtdStartTime())
                        .atdEndTime(((AttendanceRegistRequest) request.getSource()).getAtdEndTime())
                        .startTime(((AttendanceRegistRequest) request.getSource()).getStartTime())
                        .endTime(((AttendanceRegistRequest) request.getSource()).getEndTime())
                        .employee(((AttendanceRegistRequest) request.getSource()).getEmployee())
                        .attendanceStatus(((AttendanceRegistRequest) request.getSource()).getAttendanceStatus())
                        .build());
        return modelMapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

}
