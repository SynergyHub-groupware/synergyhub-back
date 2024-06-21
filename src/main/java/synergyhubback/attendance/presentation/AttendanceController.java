package synergyhubback.attendance.presentation;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synergyhubback.attendance.domain.entity.Attendance;
import synergyhubback.attendance.dto.request.AttendanceRegistEndTimeRequest;
import synergyhubback.attendance.dto.request.AttendanceRegistStartTimeRequest;
import synergyhubback.attendance.dto.response.AttendancesResponse;
import synergyhubback.attendance.service.AttendanceService;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "http://localhost:3000")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /* 등록 기능 ------------------------------------  */

    @Operation(summary = "출근 시간 등록", description = "출근 시간을 등록한다.")
    @PostMapping("/registStartTime/{empCode}")
    public ResponseEntity<ResponseMessage> registAttendanceStartTime(@PathVariable int empCode) {

        try {

            AttendanceRegistStartTimeRequest updateAttendance = attendanceService.registAttendanceStartTime(empCode);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("attendance", updateAttendance);
            ResponseMessage responseMessage = new ResponseMessage(200, "출근 시간 등록 성공", responseMap);

            return new ResponseEntity<>(responseMessage, headers, HttpStatus.CREATED);

        } catch (EntityNotFoundException ex) {
            ResponseMessage responseMessage = new ResponseMessage(404, "근태 기록을 찾을 수 없습니다.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
    }

    //퇴근시간 등록기능

    @Operation(summary = "퇴근 시간 등록", description = "퇴근 시간을 등록한다.")
    @PostMapping("/registEndTime/{empCode}")
    public ResponseEntity<ResponseMessage> registAttendanceEndTime(@PathVariable int empCode) {


        try {
            AttendanceRegistEndTimeRequest updateAttendance = attendanceService.registAttendanceEndTime(empCode);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("attendance", updateAttendance);
            ResponseMessage responseMessage = new ResponseMessage(200, "퇴근 시간 등록 성공", responseMap);

            return new ResponseEntity<>(responseMessage, headers, HttpStatus.CREATED);

        } catch (EntityNotFoundException ex) {
            ResponseMessage responseMessage = new ResponseMessage(404, "근태 기록을 찾을 수 없습니다.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
    }

    /* 조회 기능 ------------------------------------ */

    @Operation(summary = "전체 근태 기록 조회", description = "전체 근태 목록을 조회한다.")
    @GetMapping("/all")
    public ResponseEntity<ResponseMessage> findAllAttendances() {

        /* 전체 근태 기록 조회 */
        List<AttendancesResponse> attendances = attendanceService.findAllAttendances();

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

        /* 현재 주의 근태 기록 조회 */
        List<AttendancesResponse> attendancesForCurrentWeek = attendanceService.getAttendancesForCurrentWeek();

        /* 응답 헤더 설정 */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        /* 응답 데이터 설정 */
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("attendances", attendancesForCurrentWeek);

        /* 응답 메시지 생성 */
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @Operation(summary = "오늘의 근태 기록 조회", description = "오늘의 근태 기록을 조회한다.")
    @GetMapping("/today/{empCode}")
    public ResponseEntity<ResponseMessage> getAttendanceForToday(@PathVariable int empCode) {

        /* 오늘의 근태 기록 조회 */
        AttendancesResponse attendancesForToday = attendanceService.getAttendancesForToday(empCode);

        /* 응답 헤더 설정 */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        /* 응답 데이터 설정 */
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("attendances", attendancesForToday);

        /* 응답 메시지 생성 */
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

}
