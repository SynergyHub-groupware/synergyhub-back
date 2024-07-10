package synergyhubback.employee.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class AppRegistGroupRequest {


    private String aappNo;       // 발령 번호

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate aappDate;       // 발령 일

    private String aappTitle;       // 발령 제목

    private int adetCode;      // 발령 결재 상세 코드

    private String aappCode;       // 발령 결재 코드

    private String adetBefore;     // 발령 전

    private String adetAfter;      // 발령 후

    private String adetType;       // 발령 종류

    private String empCode;        // 사원 코드



    public AppRegistGroupRequest() {

    }
}
