package synergyhubback.approval.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "APPROVAL_APPOINT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ApprovalAppoint {
    @Id
    private String aappCode;
    private String aappNo;
    private LocalDate aappDate;
    private String aappTitle;

    public static ApprovalAppoint of(String aappCode, String aappNo, LocalDate aappDate, String aappTitle){
        return new ApprovalAppoint(aappCode, aappNo, aappDate, aappTitle);
    }
}
