package synergyhubback.pheed.presentation;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import synergyhubback.auth.util.TokenUtils;
import synergyhubback.pheed.dto.request.PheedCreateRequest;
import synergyhubback.pheed.service.PheedService;

@RestController
@RequestMapping("/api/pheed")
public class PheedController {

    private final PheedService pheedService;

    public PheedController(PheedService pheedService) {
        this.pheedService = pheedService;
    }

    @GetMapping(value = "/update", produces = "text/event-stream")
    public SseEmitter pheedUpdate(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request); // HTTP 요청에서 JWT 추출
        String username = TokenUtils.getEmp_Name(jwt); // JWT에서 사용자 이름 추출
        return pheedService.pheedUpdate(username, "");
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        // HTTP 요청에서 Authorization 헤더를 가져옵니다.
        String authorizationHeader = request.getHeader("Authorization");

        // Authorization 헤더의 값이 비어있거나 Bearer 토큰이 아닌 경우 null을 반환합니다.
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Bearer 토큰에서 실제 JWT를 추출하여 반환합니다.
            return authorizationHeader.substring(7); // "Bearer " 이후의 토큰을 반환합니다.
        }

        return null; // JWT가 없는 경우 null을 반환합니다.
    }

    /* 피드 생성 */
    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<String> createPheed(@RequestBody PheedCreateRequest request) {
        try {
            pheedService.send(request.getPheedCode(), request.getPheedCon(), request.getPheedSort(), request.getEmployee());
            return ResponseEntity.status(HttpStatus.CREATED).body("Pheed created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /* 피드 조회 */



}
