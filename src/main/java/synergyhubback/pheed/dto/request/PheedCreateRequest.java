package synergyhubback.pheed.dto.request;

import lombok.Getter;
import lombok.Setter;
import synergyhubback.employee.domain.entity.Employee;

import java.time.LocalTime;

@Getter
@Setter
public class PheedCreateRequest {

    private int pheedCode;           // 피드 코드
    private String pheedCon;         // 피드내용
    private LocalTime creStatus;     // 생성시간
    private String readStatus;       // 읽음상태
    private String deStatus;         // 삭제상태
    private String pheedSort;        // 피드분류
    private Employee employee;       // 사원코드
    private String url;              // 링크

}
