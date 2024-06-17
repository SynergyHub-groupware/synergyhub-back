package synergyhubback.approval.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "TRUE_APP_LINE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TrueLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int talCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adCode")
    private Document document;

    private int talOrder;
    private String talRole;
    private String talStatus;
    private int empCode;
    private String talReason;
    private LocalDate talDate;

    private TrueLine(Document document, int talOrder, String talRole, String talStatus, int empCode){
        this.document = document;
        this.talOrder = talOrder;
        this.talRole = talRole;
        this.talStatus = talStatus;
        this.empCode = empCode;
    }
    public static TrueLine of(Document document, int talOrder, String talRole, String talStatus, int empCode){
        return new TrueLine(document, talOrder, talRole, talStatus, empCode);
    }

    private TrueLine(Document document, int talOrder, String talRole, String talStatus, int empCode, LocalDate talDate){
        this.document = document;
        this.talOrder = talOrder;
        this.talRole = talRole;
        this.talStatus = talStatus;
        this.empCode = empCode;
        this.talDate = talDate;
    }
    public static TrueLine of(Document document, int talOrder, String talRole, String talStatus, int empCode, LocalDate talDate){
        return new TrueLine(document, talOrder, talRole, talStatus, empCode, talDate);
    }

    private TrueLine(Document document, int talOrder, String talRole, String talStatus, int empCode, String talReason, LocalDate talDate){
        this.document = document;
        this.talOrder = talOrder;
        this.talRole = talRole;
        this.talStatus = talStatus;
        this.empCode = empCode;
        this.talReason = talReason;
        this.talDate = talDate;
    }
    public static TrueLine of(Document document, int talOrder, String talRole, String talStatus, int empCode, String talReason, LocalDate talDate){
        return new TrueLine(document, talOrder, talRole, talStatus, empCode, talReason, talDate);
    }
}
