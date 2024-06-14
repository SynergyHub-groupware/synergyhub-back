package synergyhubback.calendar.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Getter
@RequiredArgsConstructor
public class LabelCreateRequest {

    @NotBlank
    private final String labelTitle;

    private final String labelCon;

    private final String labelColor;
}

