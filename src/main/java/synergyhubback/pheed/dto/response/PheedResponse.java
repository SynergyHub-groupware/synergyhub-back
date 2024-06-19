package synergyhubback.pheed.dto.response;

import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.connector.Response;
import synergyhubback.employee.domain.entity.Employee;
import synergyhubback.pheed.domain.entity.Pheed;

import java.time.LocalTime;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class PheedResponse {

    private int pheedCode;           // 피드 코드
    private String pheedCon;         // 피드내용
    private LocalTime creStatus;     // 생성시간
    private String readStatus;       // 읽음상태
    private String deStatus;         // 삭제상태
    private String pheedSort;        // 피드분류
    private Employee employee;       // 사원코드

    public static PheedResponse createResponse(Pheed pheed) {
        return builder()
                .pheedCode(pheed.getPheedCode())
                .pheedCon(pheed.getPheedCon())
                .creStatus(pheed.getCreStatus())
                .readStatus(pheed.getReadStatus())
                .deStatus(pheed.getDeStatus())
                .pheedSort(pheed.getPheedSort())
                .employee(pheed.getEmployee())
                .build();
    }

}
