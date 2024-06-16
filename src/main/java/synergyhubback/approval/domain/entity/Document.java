package synergyhubback.approval.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "APPROVAL_DOC")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adCode;
    private String adTitle;
    private int empCode;
    private LocalDate adReportDate;
    private String adStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "afCode")
    private Form form;

    private String adDetail;

    private Document(String adTitle, int empCode, LocalDate adReportDate, String adStatus, Form form, String adDetail) {
        this.adTitle = adTitle;
        this.empCode = empCode;
        this.adReportDate = adReportDate;
        this.adStatus = adStatus;
        this.form = form;
        this.adDetail = adDetail;
    }

    public static Document of(String adTitle, int empCode, LocalDate adReportDate, String adStatus, Form form, String adDetail) {
        return new Document(adTitle, empCode, adReportDate, adStatus, form, adDetail);
    }
}
