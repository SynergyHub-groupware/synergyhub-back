package synergyhubback.approval.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import synergyhubback.approval.domain.entity.Document;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentResponse {
    private final String adCode;
    private final String adTitle;
    private final int emp_code;
    private final LocalDate adReportDate;
    private final String adStatus;
    private final int afCode;
    private final String adDetail;

    public static DocumentResponse from(final Document document){
        return new DocumentResponse(
                document.getAdCode(),
                document.getAdTitle(),
                document.getEmployee().getEmp_code(),
                document.getAdReportDate(),
                document.getAdStatus(),
                document.getForm().getAfCode(),
                document.getAdDetail()
        );
    }
}
