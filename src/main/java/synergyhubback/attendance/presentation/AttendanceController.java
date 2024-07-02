package synergyhubback.attendance.presentation;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synergyhubback.attendance.domain.entity.DayOff;
import synergyhubback.attendance.domain.entity.DefaultSchedule;
import synergyhubback.attendance.dto.request.AttendanceRegistEndTimeRequest;
import synergyhubback.attendance.dto.request.AttendanceRegistStartTimeRequest;
import synergyhubback.attendance.dto.request.DayOffRequest;
import synergyhubback.attendance.dto.request.DefaultScheduleRequest;
import synergyhubback.attendance.dto.response.*;
import synergyhubback.attendance.service.AttendanceService;
import synergyhubback.auth.util.TokenUtils;
import synergyhubback.employee.service.EmployeeService;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "http://localhost:3000")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final EmployeeService employeeService;

    // 날짜 포맷을 상수로 정의
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public AttendanceController(AttendanceService attendanceService, EmployeeService employeeService) {
        this.attendanceService = attendanceService;
        this.employeeService = employeeService;
    }

    /* ------------------------------------ 근태일지 생성 ------------------------------------  */

    //출근시간 등록기능
    @Operation(summary = "출근 시간 등록", description = "출근 시간을 등록한다.")
    @PostMapping("/registStartTime")
    public ResponseEntity<ResponseMessage> registAttendanceStartTime(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = TokenUtils.getToken(token);
            String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
            int empCode = Integer.parseInt(tokenEmpCode);

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
    @PostMapping("/registEndTime")
    public ResponseEntity<ResponseMessage> registAttendanceEndTime(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = TokenUtils.getToken(token);
            String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
            int empCode = Integer.parseInt(tokenEmpCode);

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

    /* ------------------------------------ 지정 출퇴근시간 ------------------------------------ */

    // 지정 출퇴근시간 등록
    @Operation(summary = "지정 출퇴근시간 등록", description = "지정 출퇴근시간을 등록한다.")
    @PostMapping(value = "/registSchedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage> registDefaultSchedule(@RequestBody DefaultScheduleRequest request) {
        try {
            // Null 체크 추가
            if (request.getDeptCode() == null) {
                throw new IllegalArgumentException("DeptTitle이 null입니다.");
            } else if (request.getAtdStartTime() == null) {
                throw new IllegalArgumentException("StartTime이 null입니다.");
            } else if (request.getAtdEndTime() == null) {
                throw new IllegalArgumentException("endTime이 null입니다.");
            }

            // 존재 여부 체크
            List<DefaultSchedule> existingSchedules = attendanceService.findDefaultSchedules(
                    request.getDeptCode(), request.getParsedStartTime(), request.getParsedEndTime());
            if (!existingSchedules.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(new ResponseMessage(409, "이미 등록되어 있습니다.", null));
            }

            // 지정 출퇴근시간 등록
            attendanceService.registDefaultSchedule(request.getDeptCode(), request.getParsedStartTime(), request.getParsedEndTime(), request.getEmployee());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseMessage(200, "지정 출퇴근시간 등록 성공", null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage(500, "서버 오류: " + e.getMessage(), null));
        }
    }

//    // 지정 출퇴근시간 조회 (개인)
//    @Operation(summary = "전체 근태 기록 조회", description = "전체 근태 목록을 조회한다.")
//    @GetMapping("/allSchedules")
//    public ResponseEntity<ResponseMessage> findMySchedules(@RequestHeader("Authorization") String token) {
//
//        String jwtToken = TokenUtils.getToken(token);
//        String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
//        int empCode = Integer.parseInt(tokenEmpCode);
//
//        List<DefaultScheduleResponse> schedules = attendanceService.findMyDefaultSchedules(empCode);
//        HttpHeaders headers = new HttpHeaders();
//
//        Map<String, Object> responseMap = new HashMap<>();
//        responseMap.put("schedules", schedules);
//        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(responseMessage);
//    }

    // 지정 출퇴근시간 조회 (단체)
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
            if (request.getDeptCode() == null) {
                throw new IllegalArgumentException("DeptTitle이 null입니다.");
            } else if (request.getAtdStartTime() == null) {
                throw new IllegalArgumentException("StartTime이 null입니다.");
            } else if (request.getAtdEndTime() == null) {
                throw new IllegalArgumentException("endTime이 null입니다.");
            }

            // 출퇴근시간 수정
            attendanceService.updateDefaultSchedule(request.getDeptCode(), request.getParsedStartTime(), request.getParsedEndTime(), request.getEmployee());

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
    @Operation(summary = "지정 출퇴근시간 삭제", description = "지정 출퇴근시간을 삭제한다.")
    @DeleteMapping("/DeleteSchedule/{dsCode}")
    public ResponseEntity<ResponseMessage> deleteSchedule(@PathVariable int dsCode) {
        boolean deleted = attendanceService.deleteSchedule(dsCode);
        if(deleted) {
            return ResponseEntity.ok().body(new ResponseMessage(200, "삭제 성공", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(404, "삭제할 데이터가 없습니다.", null));
        }
    }


    /* ------------------------------------ 근태 기록 ------------------------------------ */

    // 개인별
    @Operation(summary = "이번 주의 근태 기록 조회", description = "이번 주의 근태 목록을 조회한다.")
    @GetMapping("/my-current-week")
    public ResponseEntity<ResponseMessage> getMyAttendancesForCurrentWeek(@RequestHeader("Authorization") String token) {

        String jwtToken = TokenUtils.getToken(token);
        String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
        int empCode = Integer.parseInt(tokenEmpCode);

        List<AttendancesResponse> attendancesForCurrentWeek = attendanceService.getMyAttendancesForCurrentWeek(empCode);
        HttpHeaders headers = new HttpHeaders();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("attendances", attendancesForCurrentWeek);

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ResponseMessage(200, "조회 성공", responseMap));
    }

    // 개인
    @Operation(summary = "전체 근태 기록 조회", description = "전체 근태 목록을 조회한다.")
    @GetMapping("/my-all")
    public ResponseEntity<ResponseMessage> findAllAttendances(@RequestHeader("Authorization") String token) {

        String jwtToken = TokenUtils.getToken(token);
        String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
        int empCode = Integer.parseInt(tokenEmpCode);

        List<AttendancesResponse> attendances = attendanceService.findAllMyAttendances(empCode);
        HttpHeaders headers = new HttpHeaders();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("attendances", attendances); // 복수형으로 변경: attendance -> attendances
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseMessage);
    }


    // 전체
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
    @GetMapping("/today")
    public ResponseEntity<ResponseMessage> getAttendanceForToday(@RequestHeader("Authorization") String token) {

        String jwtToken = TokenUtils.getToken(token);
        String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
        int empCode = Integer.parseInt(tokenEmpCode);

        AttendancesResponse attendancesForToday = attendanceService.getAttendancesForToday(empCode);
        HttpHeaders headers = new HttpHeaders();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("attendance", attendancesForToday); // 단수형으로 변경: attendances -> attendance

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ResponseMessage(200, "조회 성공", responseMap));
    }

    /* ------------------------------------ 초과근무 ------------------------------------ */

    @Operation(summary = "초과근무 기록 조회", description = "초과근무 기록을 조회한다.")
    @GetMapping("/overwork")
    public ResponseEntity<ResponseMessage> findAllOverTimeWork() {
        List<OverWorkResponse> overWorks = attendanceService.findAllOverTimeWork();
        HttpHeaders headers = new HttpHeaders();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("overWorks", overWorks);
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseMessage);
    }

    /* ------------------------------------ 휴가 ------------------------------------ */

    // 휴가 전체 조회
    @Operation(summary = "휴가 기록 조회", description = "휴가 기록을 조회한다.")
    @GetMapping("/dayOff")
    public ResponseEntity<ResponseMessage> findAllDayOff() {

        List<DayOffResponse> dayOffs = attendanceService.findAllDayOff();
        HttpHeaders headers = new HttpHeaders();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("dayOffs", dayOffs);
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseMessage);
    }

    // 휴가 개인 기록 조회
    @Operation(summary = "휴가 기록 조회", description = "휴가 기록을 조회한다.")
    @GetMapping("/my-dayOff")
    public ResponseEntity<ResponseMessage> findAllDayOff(@RequestHeader("Authorization") String token) {

        String jwtToken = TokenUtils.getToken(token);
        String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
        int empCode = Integer.parseInt(tokenEmpCode);

        List<DayOffResponse> dayOffs = attendanceService.findAllDayOffByEmpCode(empCode);
        HttpHeaders headers = new HttpHeaders();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("dayOffs", dayOffs);
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseMessage);
    }

    // 개인 휴가 보유 내역 조회
    @Operation(summary = "개인 휴가 보유 내역 조회", description = "개인의 휴가 보유 내역을 조회한다.")
    @GetMapping("/my-dayOffBalance")
    public ResponseEntity<ResponseMessage> findAllDayOffBalance(@RequestHeader("Authorization") String token) {

        String jwtToken = TokenUtils.getToken(token);
        String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
        int empCode = Integer.parseInt(tokenEmpCode);

        DayOffBalanceResponse dayOffBalance = attendanceService.findAllDayOffBalanceByEmpCode(empCode);
        HttpHeaders headers = new HttpHeaders();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("dayOffBalance", dayOffBalance);
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseMessage);
    }

    // 휴가 등록
    @Operation(summary = "휴가 등록", description = "휴가를 등록한다.")
    @PostMapping(value = "/registDayOff", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage> registDayOff(@RequestBody DayOffRequest request) {
        try {
            // Null 체크 추가
            if (request.getDoStartDate() == null) {
                throw new IllegalArgumentException("시작일자를 입력하지 않았습니다.");
            } else if (request.getDoEndDate() == null) {
                throw new IllegalArgumentException("종료일자를 입력하지 않았습니다..");
            } else if (request.getDoStartTime() == null) {
                throw new IllegalArgumentException("시작시간을 입력하지 않았습니다.");
            } else if (request.getDoEndTime() == null) {
                throw new IllegalArgumentException("종료시간을 입력하지 않았습니다.");
            } else if (request.getDoName() == null) {
                throw new IllegalArgumentException("휴가 종류를 입력하지 않았습니다.");
            }

//            // Date 형식의 날짜를 문자열로 변환
//            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate startDate = request.getDoStartDate(); // 날짜 객체
//            String formattedDate = startDate.format(dateFormatter); // 날짜 객체를 문자열로 변환
//
//            // 존재 여부 체크
//            DayOffResponse existingDayOff = attendanceService.findDayOffResearch(
//                    request.getEmployee().getEmp_code(), formattedDate);
//            if (existingDayOff != null) {
//                return ResponseEntity
//                        .status(HttpStatus.CONFLICT)
//                        .body(new ResponseMessage(409, "이미 등록된 내역이 있습니다.", null));
//            }

            // 휴가 등록
            attendanceService.registDayOff(
                    request.getDoReportDate(),
                    request.getDoName(), request.getDoUsed(),
                    request.getDoStartDate(), request.getDoEndDate(),
                    request.getDoStartTime(), request.getDoEndTime(),
                    request.getEmployee());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseMessage(200, "휴가 등록 성공", null));

        } catch(Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage(500, "서버 오류: " + e.getMessage(), null));
        }
    }

}
