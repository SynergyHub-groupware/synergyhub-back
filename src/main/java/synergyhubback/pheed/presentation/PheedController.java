package synergyhubback.pheed.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
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
    public SseEmitter pheedUpdate(@AuthenticationPrincipal User principal,
                                  @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return pheedService.pheedUpdate(principal.getUsername(), lastEventId);
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<String> createPheed(@RequestBody PheedCreateRequest request) {
        try {
            pheedService.send(request.getPheedCode(), request.getPheedCon(), request.getPheedSort(), request.getEmployee());
            return ResponseEntity.status(HttpStatus.CREATED).body("Pheed created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
