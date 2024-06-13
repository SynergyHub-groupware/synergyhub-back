package synergyhubback.approval.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import synergyhubback.approval.domain.entity.Line;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FormLineResponse {
    private final int lsCode;
    private final String alSort;
    private final int alOrder;
    private final String alRole;

    public static FormLineResponse from(Line line){
        return new FormLineResponse(
            line.getLineSort().getLsCode(),
            line.getAlSort(),
            line.getAlOrder(),
            line.getAlRole()
        );
    }
}
