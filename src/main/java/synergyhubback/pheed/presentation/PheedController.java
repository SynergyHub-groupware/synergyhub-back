package synergyhubback.pheed.presentation;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import synergyhubback.attendance.presentation.ResponseMessage;
import synergyhubback.auth.util.TokenUtils;
import synergyhubback.employee.dto.response.MyInfoResponse;
import synergyhubback.employee.service.EmployeeService;
import synergyhubback.pheed.domain.entity.Pheed;
import synergyhubback.pheed.dto.request.PheedCreateRequest;
import synergyhubback.pheed.dto.response.PheedResponse;
import synergyhubback.pheed.service.PheedService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/pheed")
public class PheedController {

    private final PheedService pheedService;
    private final EmployeeService employeeService;
    private final TokenUtils tokenUtils;

    public PheedController(PheedService pheedService, EmployeeService employeeService, TokenUtils tokenUtils) {
        this.pheedService = pheedService;
        this.employeeService = employeeService;
        this.tokenUtils = tokenUtils;
    }

    public static Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    /* 구독 기능 */
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@RequestHeader("Authorization") String token,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {

        String jwtToken = TokenUtils.getToken(token);
        String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
        int empCode = Integer.parseInt(tokenEmpCode);

        MyInfoResponse myInfoResponse = employeeService.getMyInfo(empCode);

        return pheedService.subscribe(myInfoResponse.getEmp_code(), lastEventId);
    }

    /* 피드 생성 */
    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<String> createPheed(@RequestBody PheedCreateRequest request) {
        try {
            pheedService.send(request.getPheedCode(), request.getPheedCon(), request.getPheedSort(), request.getEmployee(), request.getUrl());
            return ResponseEntity.status(HttpStatus.CREATED).body("Pheed created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /* 피드 조회 */
    @GetMapping("/list")
    public ResponseEntity<ResponseMessage> getAllPheeds(@RequestHeader("Authorization") String token) {

        /* 토큰을 통한 피드 조회 */
        String jwtToken = TokenUtils.getToken(token);
        int userId = Integer.parseInt(TokenUtils.getEmp_Code(jwtToken));
        List<PheedResponse> pheeds = pheedService.getAllPheeds(userId);

        /* 응답 헤더 설정 */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        /* 응답 데이터 설정 */
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("pheeds", pheeds);

        /* 응답 메시지 생성 */
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }








}
