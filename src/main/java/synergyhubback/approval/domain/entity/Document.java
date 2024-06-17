package synergyhubback.approval.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "APPROVAL_DOC")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Document {
    @Id
    private String adCode;
    private String adTitle;
    private int empCode;
    private LocalDate adReportDate;
    private String adStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "afCode")
    private Form form;

    private String adDetail;

    public static Document of(String adCode, String adTitle, int empCode, LocalDate adReportDate, String adStatus, Form form, String adDetail) {
        return new Document(adCode, adTitle, empCode, adReportDate, adStatus, form, adDetail);
    }
}
