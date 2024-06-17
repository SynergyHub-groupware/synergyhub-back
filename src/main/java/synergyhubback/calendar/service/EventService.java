package synergyhubback.calendar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.calendar.domain.entity.Event;
import synergyhubback.calendar.domain.repository.EventRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

    private final EventRepository eventRepository;

    public Event createEvent(Event event) {
        event.setId(generateEventId()); // "CA" + 1 형식의 ID 생성
        return eventRepository.save(event);
    }

    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Optional<Event> findById(String id) {
        return eventRepository.findById(id);
    }

    public void deleteById(String id) {
        eventRepository.deleteById(id);
    }

    private String generateEventId() {
        long count = eventRepository.count();
        return "CA" + (count + 1);
    }
}
