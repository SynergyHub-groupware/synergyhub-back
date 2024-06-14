package synergyhubback.calendar.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class EventCreateRequest {

    @NotBlank
    private final String title;

    private final String eventCon;

    @NotNull
    private final LocalDateTime startDate;

    private final LocalDateTime endDate;

    private final boolean allDay;

    private final String eventGuests;

    @NotNull
    private final Long empCode;

    @NotNull
    private final Long labelCode;
}

