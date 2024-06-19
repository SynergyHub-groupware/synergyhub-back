package synergyhubback.pheed.domain.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmitterRepositoryImp implements EmitterRepository {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

    @Override
    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    @Override
    public void saveEventCache(String eventCacheId, Object event) {
        eventCache.put(eventCacheId, event);
    }

    public Map<String, SseEmitter> findAllEmitterStartWithByEmpCode(String empCode) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(empCode))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, Object> findAllEventCacheStartWithByEmpCode(String empCode) {
        return eventCache.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(empCode))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void deleteByPheedCode(String pheedCode) {
        emitters.remove(pheedCode);
    }

    @Override
    public void deleteAllEmitterStartWithPheedCode(String empCode) {
        emitters.forEach(
                (key, emitter) -> {
                    if (key.startsWith(empCode)) {
                        emitters.remove(key);
                    }
                }
        );
    }

    @Override
    public void deleteAllEventCacheStartWithPheedCode(String empCode) {
        eventCache.forEach(
                (key, emitter) -> {
                    if (key.startsWith(empCode)) {
                        eventCache.remove(key);
                    }
                }
        );
    }


}
