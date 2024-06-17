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

    @NotBlank
    private final String eventCon;

    @NotNull
    private final LocalDateTime startDate;

    private final LocalDateTime endDate;

    private final boolean allDay;

    private final String eventGuests;

    @NotNull
    private final Integer empCode; // 사원 코드

    @NotNull
    private final Long labelCode; // 라벨 코드
}
