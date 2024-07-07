package synergyhubback.pheed.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
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
    private int empCode;             // 사원코드
    private String empName;
    private String url;              // 링크

    public static PheedResponse pheedResponse(Pheed pheed) {
        return builder()
                .pheedCon(pheed.getPheedCon())
                .creStatus(pheed.getCreStatus())
                .readStatus(pheed.getReadStatus())
                .deStatus(pheed.getDeStatus())
                .pheedSort(pheed.getPheedSort())
                .empCode(pheed.getEmployee().getEmp_code())
                .empName(pheed.getEmployee().getEmp_name())
                .url(pheed.getUrl())
                .build();
    }

}
