package synergyhubback.calendar.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class TaskUpdateRequest {

    @NotBlank
    private final String title;

    private final LocalDate modifiedDate;

    @NotNull
    private final LocalDateTime start;

    private final LocalDateTime end;

    @NotBlank
    private final String status;

    @NotBlank
    private final String priority;

    @NotBlank
    private final String description;

    @NotBlank
    private final String owner;

    private final boolean display;
}
