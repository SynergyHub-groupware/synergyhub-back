package synergyhubback.pheed.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PheedService {

    private static final Long DEFAULT_TIMEOUT = 7L * 24 * 60 * 60 * 1000;

    private final EmitterRepository emitterRepository;
    private final PheedRepository pheedRepository;
    private final EmployeeRepository employeeRepository;

    public PheedService(EmitterRepository emitterRepository, PheedRepository pheedRepository, EmployeeRepository employeeRepository) {
        this.emitterRepository = emitterRepository;
        this.pheedRepository = pheedRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void send(int pheedCode, String pheedCon, String pheedSort, Employee employee, String url) {
        Employee fetchedEmployee = employeeRepository.findByEmpCode(employee.getEmp_code());
        if (fetchedEmployee == null) {
            throw new IllegalArgumentException("Employee not found with empCode: " + employee.getEmp_code());
        }

        Pheed pheed = Pheed.builder()
                .pheedCode(pheedCode)
                .pheedCon(pheedCon)
                .creStatus(LocalDateTime.now())
                .readStatus("N")
                .deStatus("N")
                .pheedSort(pheedSort)
                .employee(fetchedEmployee)
                .url(url)
                .build();

        Pheed savedPheed = pheedRepository.save(pheed);

        String empCode = String.valueOf(fetchedEmployee.getEmp_code());
        String eventId = empCode + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByEmpCode(empCode);
        emitters.forEach((key, emitter) -> {
            emitterRepository.saveEventCache(key, savedPheed);
            sendPheedResponse(emitter, eventId, key, PheedResponse.createResponse(savedPheed));
        });
    }

    private void sendPheedResponse(SseEmitter emitter, String eventId, String emitterId, PheedResponse pheedResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(pheedResponse);

            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("sse")
                    .data(json)
            );
        } catch (IOException exception) {
            handleEmitterException(emitter, emitterId);
        }
    }

    private void handleEmitterException(SseEmitter emitter, String emitterId) {
        emitterRepository.deleteByPheedCode(emitterId);
        emitter.complete();
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
                .forEach(entry -> {
                    if (entry.getValue() instanceof Pheed) {
                        sendPheedResponse(emitter, entry.getKey(), emitterId, PheedResponse.createResponse((Pheed) entry.getValue()));
                    } else {
                        handleEmitterException(emitter, emitterId);
                    }
                });
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
                .creStatus(LocalDateTime.now())
                .readStatus("N")
                .deStatus("N")
                .pheedSort(request.getPheedSort())
                .employee(employee)
                .url(request.getUrl())
                .build();

        pheedRepository.save(pheed);
    }

    @Transactional
    public SseEmitter subscribe(int userId, String lastEventId) {
        String emitterId = makeTimeIncludeId(String.valueOf(userId));
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        emitterRepository.save(emitterId, emitter);

        emitter.onCompletion(() -> {
            emitter.complete();
        });

        emitter.onTimeout(() -> {
            emitter.complete();
            emitterRepository.deleteById(emitterId);
        });

        // 클라이언트에게 실제 피드 데이터 전송
        List<Pheed> pheedList = pheedRepository.findByEmployeeEmp_code(userId);
        for (Pheed pheed : pheedList) {
            sendPheedResponse(emitter, makeTimeIncludeId(String.valueOf(userId)), emitterId, PheedResponse.createResponse(pheed));
        }

        // 클라이언트가 미수신한 Event 목록이 존재할 경우, 전송하여 Event 유실을 예방
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, userId, emitterId, emitter);
        }

        return emitter;
    }

    // 모든 피드 조회 메서드
    @Transactional(readOnly = true)
    public List<PheedResponse> getAllPheeds(int empCode) {
        List<Pheed> pheeds = pheedRepository.findByEmployeeEmp_code(empCode);

        return pheeds.stream()
                .map(PheedResponse::createResponse)
                .collect(Collectors.toList());
    }

}
