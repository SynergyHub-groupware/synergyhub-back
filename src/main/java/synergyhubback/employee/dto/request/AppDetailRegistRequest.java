package synergyhubback.employee.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class AppDetailRegistRequest {

    private int adetCode;      // 발령 결재 상세 코드

    private String aappCode;       // 발령 결재 코드

    private String adetBefore;     // 발령 전

    private String adetAfter;      // 발령 후

    private String adetType;       // 발령 종류

    private String empCode;        // 사원 코드

    public AppDetailRegistRequest() {

    }
}
