package synergyhubback.calendar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.calendar.domain.entity.Event;
import synergyhubback.calendar.domain.entity.Label;
import synergyhubback.calendar.domain.repository.EventRepository;
import synergyhubback.calendar.domain.repository.LabelRepository;
import synergyhubback.employee.domain.entity.Employee;
import synergyhubback.employee.domain.repository.EmployeeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final EmployeeRepository employeeRepository;
    private final LabelRepository labelRepository;

    public Event createEvent(String title, String eventCon, LocalDateTime startDate, LocalDateTime endDate,
                             boolean allDay, String eventGuests, int empCode, long labelCode) {

        Employee employee = findEmployeeById(empCode);
        Label label = findLabelById(labelCode);

        Event event = Event.createEvent(
                title, eventCon, startDate, endDate, allDay, eventGuests, employee, label
        );

        event.setId(generateEventId()); // 이벤트 ID 생성 로직
        return eventRepository.save(event);
    }

    public Event updateEvent(String eventId, String title, String eventCon, LocalDateTime startDate, LocalDateTime endDate,
                             boolean allDay, String eventGuests, int empCode, long labelCode) {

        Event event = findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));

        Employee employee = findEmployeeById(empCode);
        Label label = findLabelById(labelCode);

        event.setTitle(title);
        event.setEventCon(eventCon);
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        event.setAllDay(allDay);
        event.setEventGuests(eventGuests);
        event.setEmployee(employee);
        event.setLabel(label);

        return eventRepository.save(event);
    }

    public void deleteEvent(String eventId) {
        eventRepository.deleteById(eventId);
    }

    public Optional<Event> findById(String eventId) {
        return eventRepository.findById(eventId);
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Employee findEmployeeById(int empCode) {
        return employeeRepository.findById(empCode)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public Label findLabelById(long labelCode) {
        return labelRepository.findById(labelCode)
                .orElseThrow(() -> new RuntimeException("Label not found"));
    }

    private String generateEventId() {
        long count = eventRepository.count();
        return "CA" + (count + 1);
    }
}
