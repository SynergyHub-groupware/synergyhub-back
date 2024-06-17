package synergyhubback.calendar.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import synergyhubback.calendar.domain.entity.Event;
import synergyhubback.calendar.domain.entity.Label;
import synergyhubback.calendar.dto.request.EventCreateRequest;
import synergyhubback.calendar.dto.request.EventUpdateRequest;
import synergyhubback.calendar.dto.response.EventResponse;
import synergyhubback.calendar.service.EventService;
import synergyhubback.calendar.service.LabelService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
//    private final EmployeeService employeeService;
    private final LabelService labelService;

//    @PostMapping("/event/regist")
//    public EventResponse createEvent(@RequestBody EventCreateRequest eventRequest) {
//        Employee employee = employeeService.findOptionalByEmpCode(eventRequest.getEmpCode())
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//        Label label = labelService.findById(eventRequest.getLabelCode()).orElseThrow(() -> new RuntimeException("Label not found"));
//
//        Event event = Event.createEvent(
//                eventRequest.getTitle(),
//                eventRequest.getEventCon(),
//                eventRequest.getStartDate(),
//                eventRequest.getEndDate(),
//                eventRequest.isAllDay(),
//                eventRequest.getEventGuests(),
//                employee,
//                label
//        );
//
//        Event savedEvent = eventService.createEvent(event);
//        return EventResponse.from(savedEvent);
//    }
//
//    @PutMapping("/event/{eventId}")
//    public EventResponse updateEvent(@PathVariable String eventId, @RequestBody EventUpdateRequest eventRequest) {
//        Employee employee = employeeService.findOptionalByEmpCode(eventRequest.getEmpCode())
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//        Label label = labelService.findById(eventRequest.getLabelCode()).orElseThrow(() -> new RuntimeException("Label not found"));
//
//        Event event = eventService.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
//        event.setTitle(eventRequest.getTitle());
//        event.setEventCon(eventRequest.getEventCon());
//        event.setStartDate(eventRequest.getStartDate());
//        event.setEndDate(eventRequest.getEndDate());
//        event.setAllDay(eventRequest.isAllDay());
//        event.setEventGuests(eventRequest.getEventGuests());
//        event.setEmployee(employee);
//        event.setLabel(label);
//
//        Event updatedEvent = eventService.updateEvent(event);
//        return EventResponse.from(updatedEvent);
//    }

//    @PatchMapping("/event/{eventId}")
//    public EventResponse updateEventPartial(@PathVariable String eventId, @RequestBody EventUpdateRequest eventRequest) {
//        Employee employee = employeeService.findOptionalByEmpCode(eventRequest.getEmpCode())
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//        Label label = labelService.findById(eventRequest.getLabelCode()).orElseThrow(() -> new RuntimeException("Label not found"));
//
//        Event event = eventService.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
//
//        if (eventRequest.getTitle() != null) event.setTitle(eventRequest.getTitle());
//        if (eventRequest.getEventCon() != null) event.setEventCon(eventRequest.getEventCon());
//        if (eventRequest.getStartDate() != null) event.setStartDate(eventRequest.getStartDate());
//        if (eventRequest.getEndDate() != null) event.setEndDate(eventRequest.getEndDate());
//        event.setAllDay(eventRequest.isAllDay());
//        if (eventRequest.getEventGuests() != null) event.setEventGuests(eventRequest.getEventGuests());
//        event.setEmployee(employee);
//        event.setLabel(label);
//
//        Event updatedEvent = eventService.updateEvent(event);
//        return EventResponse.from(updatedEvent);
//    }

    @GetMapping("/events")
    public List<EventResponse> getAllEvents() {
        return eventService.findAll().stream().map(EventResponse::from).collect(Collectors.toList());
    }

    @GetMapping("/event/{eventId}")
    public EventResponse getEventById(@PathVariable String eventId) {
        Event event = eventService.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        return EventResponse.from(event);
    }

    @DeleteMapping("/event/{eventId}")
    public void deleteEvent(@PathVariable String eventId) {
        eventService.deleteById(eventId);
    }
}