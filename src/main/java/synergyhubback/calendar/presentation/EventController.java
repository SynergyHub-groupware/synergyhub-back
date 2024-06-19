package synergyhubback.calendar.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import synergyhubback.calendar.domain.entity.Event;
import synergyhubback.calendar.dto.request.EventCreateRequest;
import synergyhubback.calendar.dto.request.EventUpdateRequest;
import synergyhubback.calendar.dto.response.EventResponse;
import synergyhubback.calendar.service.EventService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("/event/regist")
    public EventResponse createEvent(@RequestBody EventCreateRequest eventRequest) {
        Event event = eventService.createEvent(
                eventRequest.getTitle(),
                eventRequest.getEventCon(),
                eventRequest.getStartDate(),
                eventRequest.getEndDate(),
                eventRequest.isAllDay(),  // boolean 타입의 getter 메서드는 isAllDay로 사용
                eventRequest.getEventGuests(),
                eventRequest.getEmpCode(),
                eventRequest.getLabelCode()
        );
        return EventResponse.from(event);
    }

    @PutMapping("/event/{eventId}")
    public EventResponse updateEvent(@PathVariable String eventId, @RequestBody EventUpdateRequest eventRequest) {
        Event event = eventService.updateEvent(
                eventId,
                eventRequest.getTitle(),
                eventRequest.getEventCon(),
                eventRequest.getStartDate(),
                eventRequest.getEndDate(),
                eventRequest.isAllDay(),  // boolean 타입의 getter 메서드는 isAllDay로 사용
                eventRequest.getEventGuests(),
                eventRequest.getEmpCode(),
                eventRequest.getLabelCode()
        );
        return EventResponse.from(event);
    }

    @GetMapping("/events")
    public List<EventResponse> getAllEvents() {
        return eventService.findAll().stream().map(EventResponse::from).collect(Collectors.toList());
    }

    @GetMapping("/event/{eventId}")
    public EventResponse getEventById(@PathVariable String eventId) {
        return eventService.findById(eventId)
                .map(EventResponse::from)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @DeleteMapping("/event/{eventId}")
    public void deleteEvent(@PathVariable String eventId) {
        eventService.deleteEvent(eventId);
    }
}
