package synergyhubback.attendance.presentation;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synergyhubback.attendance.domain.entity.Attendance;
import synergyhubback.attendance.domain.entity.DefaultSchedule;
import synergyhubback.attendance.dto.request.AttendanceRegistEndTimeRequest;
import synergyhubback.attendance.dto.request.AttendanceRegistStartTimeRequest;
import synergyhubback.attendance.dto.request.DefaultScheduleRequest;
import synergyhubback.attendance.dto.response.AttendancesResponse;
import synergyhubback.attendance.dto.response.DefaultScheduleResponse;
import synergyhubback.attendance.service.AttendanceService;

import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "http://localhost:3000")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /* 근태 등록 기능 ------------------------------------  */

    //출근시간 등록기능
    @Operation(summary = "출근 시간 등록", description = "출근 시간을 등록한다.")
    @PostMapping("/registStartTime/{empCode}")
    public ResponseEntity<ResponseMessage> registAttendanceStartTime(@PathVariable int empCode) {
        try {
            AttendanceRegistStartTimeRequest updateAttendance = attendanceService.registAttendanceStartTime(empCode);

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("attendance", updateAttendance);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseMessage(200, "출근 시간 등록 성공", responseMap));

        } catch (EntityNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(404, "근태 기록을 찾을 수 없습니다.", null));
        }
    }
    //퇴근시간 등록기능
    @Operation(summary = "퇴근 시간 등록", description = "퇴근 시간을 등록한다.")
    @PostMapping("/registEndTime/{empCode}")
    public ResponseEntity<ResponseMessage> registAttendanceEndTime(@PathVariable int empCode) {
        try {
            AttendanceRegistEndTimeRequest updateAttendance = attendanceService.registAttendanceEndTime(empCode);

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("attendance", updateAttendance);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseMessage(200, "퇴근 시간 등록 성공", responseMap));

        } catch (EntityNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(404, "근태 기록을 찾을 수 없습니다.", null));
        }
    }

    /* 지정 출퇴근시간 ------------------------------------ */

    // 지정 출퇴근시간 등록
    @Operation(summary = "지정 출퇴근시간 등록", description = "지정 출퇴근시간을 등록한다.")
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/registSchedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage> registDefaultSchedule(@RequestBody DefaultScheduleRequest request) {
        try {
            // Null 체크 추가
            if (request.getDeptTitle() == null) {
                throw new IllegalArgumentException("DeptTitle이 null입니다.");
            } else if (request.getAtdStartTime() == null) {
                throw new IllegalArgumentException("StartTime이 null입니다.");
            } else if (request.getAtdEndTime() == null) {
                throw new IllegalArgumentException("endTime이 null입니다.");
            }

            // 존재 여부 체크
            List<DefaultSchedule> existingSchedules = attendanceService.findDefaultSchedules(
                    request.getDeptTitle(), request.getParsedStartTime(), request.getParsedEndTime());
            if (!existingSchedules.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(new ResponseMessage(409, "이미 등록되어 있습니다.", null));
            }

            // 지정 출퇴근시간 등록
            attendanceService.registDefaultSchedule(request.getDeptTitle(), request.getParsedStartTime(), request.getParsedEndTime(), request.getEmployee());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseMessage(200, "지정 출퇴근시간 등록 성공", null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage(500, "서버 오류: " + e.getMessage(), null));
        }
    }

    // 지정 출퇴근시간 조회
    @Operation(summary = "전체 근태 기록 조회", description = "전체 근태 목록을 조회한다.")
    @GetMapping("/allSchedules")
    public ResponseEntity<ResponseMessage> findAllSchedules() {
        List<DefaultScheduleResponse> schedules = attendanceService.findAllDefaultSchedules();
        HttpHeaders headers = new HttpHeaders();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("schedules", schedules);
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseMessage);
    }

    // 지정 출퇴근시간 수정
    @Operation(summary = "지정 출퇴근시간 수정", description = "지정 출퇴근시간을 수정한다.")
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(value = "/updateSchedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage> updateDefaultSchedule(@RequestBody DefaultScheduleRequest request) {
        try {
            // Null 체크 추가
            if (request.getDeptTitle() == null) {
                throw new IllegalArgumentException("DeptTitle이 null입니다.");
            } else if (request.getAtdStartTime() == null) {
                throw new IllegalArgumentException("StartTime이 null입니다.");
            } else if (request.getAtdEndTime() == null) {
                throw new IllegalArgumentException("endTime이 null입니다.");
            }

            // 출퇴근시간 수정
            attendanceService.updateDefaultSchedule(request.getDeptTitle(), request.getParsedStartTime(), request.getParsedEndTime(), request.getEmployee());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseMessage(200, "지정 출퇴근시간 수정 성공", null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage(500, "서버 오류: " + e.getMessage(), null));
        }
    }

    // 지정 출퇴근시간 삭제


    /* 근태 기록 ------------------------------------ */

    @Operation(summary = "전체 근태 기록 조회", description = "전체 근태 목록을 조회한다.")
    @GetMapping("/all")
    public ResponseEntity<ResponseMessage> findAllAttendances() {
        List<AttendancesResponse> attendances = attendanceService.findAllAttendances();
        HttpHeaders headers = new HttpHeaders();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("attendances", attendances); // 복수형으로 변경: attendance -> attendances
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseMessage);
    }

    @Operation(summary = "이번 주의 근태 기록 조회", description = "이번 주의 근태 목록을 조회한다.")
    @GetMapping("/current-week")
    public ResponseEntity<ResponseMessage> getAttendancesForCurrentWeek() {
        List<AttendancesResponse> attendancesForCurrentWeek = attendanceService.getAttendancesForCurrentWeek();
        HttpHeaders headers = new HttpHeaders();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("attendances", attendancesForCurrentWeek);

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ResponseMessage(200, "조회 성공", responseMap));
    }


    @Operation(summary = "오늘의 근태 기록 조회", description = "오늘의 근태 기록을 조회한다.")
    @GetMapping("/today/{empCode}")
    public ResponseEntity<ResponseMessage> getAttendanceForToday(@PathVariable int empCode) {
        AttendancesResponse attendancesForToday = attendanceService.getAttendancesForToday(empCode);
        HttpHeaders headers = new HttpHeaders();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("attendance", attendancesForToday); // 단수형으로 변경: attendances -> attendance

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ResponseMessage(200, "조회 성공", responseMap));
    }

}
