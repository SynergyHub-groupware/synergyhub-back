package synergyhubback.pheed.domain.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@Repository
public interface EmitterRepository {
    SseEmitter save(String emitterId, SseEmitter sseEmitter);
    void saveEventCache(String emitterId, Object event);
    Map<String, SseEmitter> findAllEmitterStartWithByEmpCode(String empCode);
    Map<String, Object> findAllEventCacheStartWithByEmpCode(String empCode);
    void deleteByPheedCode(String pheedCode);
    void deleteAllEmitterStartWithPheedCode(String empCode);
    void deleteAllEventCacheStartWithPheedCode(String empCode);
}
