package synergyhubback.attendance.presentation;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synergyhubback.attendance.domain.entity.Attendance;
import synergyhubback.attendance.dto.request.AttendanceRegistStartTimeRequest;
import synergyhubback.attendance.service.AttendanceService;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceControllerTest {

    private List<Attendance> attendances;
    private final AttendanceService attendanceService;

    public AttendanceControllerTest(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

//    @Operation(summary = "출근 등록", description = "출근을 등록한다.")
//    @PostMapping("/regist")
//    public ResponseEntity<Void> registAttendance(@Valid @RequestBody AttendanceRegistStartTimeRequest attendanceRegistStartTimeRequest) {
//
//        /* 근태 기록에 등록 */
//        int newAtdCode = attendances.isEmpty() ? 1 : attendances.get(attendances.size() - 1).getAtdCode() + 1;
//        attendances.add(new Attendance(newAtdCode, attendanceRegistStartTimeRequest.getStartTime()));
//
//        return ResponseEntity
//                .created(URI.create("/api/attendance/regist/" + attendances.get(attendances.size() - 1).getAtdCode()))
//                .build();
//    }

    @Operation(summary = "전체 근태 기록 조회", description = "전체 근태 목록을 조회한다.")
    @GetMapping("/all")
    public ResponseEntity<ResponseMessage> findAllAttendances() {
        List<Attendance> attendances = attendanceService.findAllAttendances();

        /* 응답 헤더 설정 */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        /* 응답 데이터 설정 */
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("attendance", attendances);
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @Operation(summary = "이번 주의 근태 기록 조회", description = "이번 주의 근태 목록을 조회한다.")
    @GetMapping("/current-week")
    public ResponseEntity<ResponseMessage> getAttendancesForCurrentWeek() {

        // 현재 주의 근태 기록 조회
        List<Attendance> attendancesForCurrentWeek = attendanceService.getAttendancesForCurrentWeek();

        // 응답 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        // 응답 데이터 설정
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("attendances", attendancesForCurrentWeek);

        // 응답 메시지 생성
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }





}
