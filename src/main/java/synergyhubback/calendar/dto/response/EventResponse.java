package synergyhubback.calendar.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import synergyhubback.calendar.domain.entity.Event;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EventResponse {

    private final String id;
    private final String title;
    private final String eventCon;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final boolean allDay;
    private final String eventGuests;

    public static EventResponse from(Event event) {
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getEventCon(),
                event.getStartDate(),
                event.getEndDate(),
                event.isAllDay(),
                event.getEventGuests()
        );
    }
}