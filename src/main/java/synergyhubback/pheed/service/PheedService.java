package synergyhubback.pheed.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import synergyhubback.employee.domain.entity.Employee;
import synergyhubback.employee.domain.repository.EmployeeRepository;
import synergyhubback.pheed.domain.entity.Pheed;
import synergyhubback.pheed.domain.repository.EmitterRepository;
import synergyhubback.pheed.domain.repository.PheedRepository;
import synergyhubback.pheed.dto.request.PheedCreateRequest;
import synergyhubback.pheed.dto.response.PheedResponse;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Map;

@Service
public class PheedService {

    // 일주일
    private static final Long DEFAULT_TIMEOUT = 7L * 24 * 60 * 60 * 1000;

    private final EmitterRepository emitterRepository;
    private final PheedRepository pheedRepository;
    private final EmployeeRepository employeeRepository;

    public PheedService(EmitterRepository emitterRepository, PheedRepository pheedRepository, EmployeeRepository employeeRepository) {
        this.emitterRepository = emitterRepository;
        this.pheedRepository = pheedRepository;
        this.employeeRepository = employeeRepository;
    }

    public void send(int pheedCode, String pheedCon, String pheedSort, Employee employee) {
        Employee fetchedEmployee = employeeRepository.findByEmpCode(employee.getEmp_code());
        if (fetchedEmployee == null) {
            throw new IllegalArgumentException("Employee not found with empCode: " + employee.getEmp_code());
        }

        Pheed pheed = Pheed.builder()
                .pheedCode(pheedCode)
                .pheedCon(pheedCon)
                .creStatus(LocalTime.now())
                .readStatus("N")
                .deStatus("N")
                .pheedSort(pheedSort)
                .employee(fetchedEmployee)
                .build();

        Pheed savedPheed = pheedRepository.save(pheed);

        String empCode = String.valueOf(fetchedEmployee.getEmp_code());
        String eventId = empCode + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByEmpCode(empCode);
        emitters.forEach((key, emitter) -> {
            emitterRepository.saveEventCache(key, savedPheed);
            sendPheed(emitter, eventId, key, savedPheed);
        });
    }

    private void sendPheed(SseEmitter emitter, String eventId, String emitterId, Pheed pheed) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("sse")
                    .data(PheedResponse.createResponse(pheed))
            );
        } catch (IOException exception) {
            emitterRepository.deleteByPheedCode(emitterId);
        }
    }

    public SseEmitter pheedUpdate(String username, String lastEventId) {
        String emitterId = makeTimeIncludeId(username);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteByPheedCode(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteByPheedCode(emitterId));

        // 503 에러 방지
        String eventId = makeTimeIncludeId(username);
        sendPheed(emitter, eventId, emitterId, null);

        // 클라이언트가 미수신한 Event 목록이 존재할 경우, 전송하여 Event 유실을 예방
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, Integer.parseInt(username), emitterId, emitter);
        }

        return emitter;
    }

    private String makeTimeIncludeId(String empCode) {
        return empCode + "_" + System.currentTimeMillis();
    }

    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    private void sendLostData(String lastEventId, int empCode, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByEmpCode(String.valueOf(empCode));
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendPheed(emitter, entry.getKey(), emitterId, (Pheed) entry.getValue()));
    }

    @Transactional
    public void createPheed(PheedCreateRequest request) {
        Employee employee = employeeRepository.findByEmpCode(request.getEmployee().getEmp_code());
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found with empCode: " + request.getEmployee().getEmp_code());
        }

        Pheed pheed = Pheed.builder()
                .pheedCode(request.getPheedCode())
                .pheedCon(request.getPheedCon())
                .creStatus(LocalTime.now())
                .readStatus("N")
                .deStatus("N")
                .pheedSort(request.getPheedSort())
                .employee(employee)
                .build();

        pheedRepository.save(pheed);
    }
}
