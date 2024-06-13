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
    private String adDetail;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "afCode")
    private Form form;
}
