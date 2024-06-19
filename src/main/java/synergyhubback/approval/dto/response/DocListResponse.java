package synergyhubback.approval.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import synergyhubback.approval.domain.entity.TrueLine;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DocListResponse {
    private final String adCode;
    private final String adTitle;
    private final int emp_code;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate adReportDate;
    private final String adStatus;
    private final String afName;
    private final String adDetail;
    private final int talOrder;
    private final String talRole;
    private final String talStatus;
    private final String empName;

    public static DocListResponse from(final TrueLine trueLine){
        return new DocListResponse(
                trueLine.getDocument().getAdCode(),
                trueLine.getDocument().getAdTitle(),
                trueLine.getDocument().getEmployee().getEmp_code(),
                trueLine.getDocument().getAdReportDate(),
                trueLine.getDocument().getAdStatus(),
                trueLine.getDocument().getForm().getAfName(),
                trueLine.getDocument().getAdDetail(),
                trueLine.getTalOrder(),
                trueLine.getTalRole(),
                trueLine.getTalStatus(),
                trueLine.getEmployee().getEmp_name()
        );
    }
}
