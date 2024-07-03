package synergyhubback.employee.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import synergyhubback.employee.domain.entity.Department;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleDeptsResponse {
    private final String dept_code;
    private final String dept_title;

    public static SimpleDeptsResponse from(final Department department){
        return new SimpleDeptsResponse(
                department.getDept_code(),
                department.getDept_title()
        );
    }
}
