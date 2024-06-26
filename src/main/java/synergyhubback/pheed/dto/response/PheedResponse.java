package synergyhubback.pheed.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.connector.Response;
import synergyhubback.employee.domain.entity.Employee;
import synergyhubback.pheed.domain.entity.Pheed;

import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class PheedResponse {

    private int pheedCode;           // 피드 코드
    private String pheedCon;         // 피드내용

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creStatus;     // 생성시간

    private String readStatus;       // 읽음상태
    private String deStatus;         // 삭제상태
    private String pheedSort;        // 피드분류
    private int empCode;       // 사원코드

    public static PheedResponse createResponse(Pheed pheed) {
        return builder()
                .pheedCode(pheed.getPheedCode())
                .pheedCon(pheed.getPheedCon())
                .creStatus(pheed.getCreStatus())
                .readStatus(pheed.getReadStatus())
                .deStatus(pheed.getDeStatus())
                .pheedSort(pheed.getPheedSort())
                .empCode(pheed.getEmployee().getEmp_code()) // 여기서 사원 코드(empCode)를 설정합니다.
                .build();
    }

}
