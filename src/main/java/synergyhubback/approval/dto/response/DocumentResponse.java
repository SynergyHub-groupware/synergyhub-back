package synergyhubback.approval.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import synergyhubback.approval.domain.entity.Document;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentResponse {
    private final int adCode;
    private final String adTitle;
    private final int empCode;
    private final LocalDate adReportDate;
    private final String adDetail;
    private final String afName;

    public static DocumentResponse from(Document document){
        return new DocumentResponse(
            document.getAdCode(),
            document.getAdTitle(),
            document.getEmpCode(),
            document.getAdReportDate(),
            document.getAdDetail(),
            document.getForm().getAfName()
        );
    }
}
