package synergyhubback.calendar.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import synergyhubback.calendar.domain.entity.Event;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class EventResponse {

    private final Long id;
    private final String title;
    private final String eventCon;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final boolean allDay;
    private final String eventGuests;
    private final Long empCode;
    private final String empName; // Assuming Employee entity has a name field
    private final Long labelCode;
    private final String labelTitle; // Assuming Label entity has a title field

    public static EventResponse from(final Event event) {
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getEventCon(),
                event.getStartDate(),
                event.getEndDate(),
                event.isAllDay(),
                event.getEventGuests(),
                event.getEmployee().getId(),
                event.getEmployee().getName(),
                event.getLabel().getId(),
                event.getLabel().getLabelTitle()
        );
    }
}
