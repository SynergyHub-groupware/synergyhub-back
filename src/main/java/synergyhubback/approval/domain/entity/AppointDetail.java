package synergyhubback.approval.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "APPOINT_DETAIL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AppointDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adetCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aappCode")
    private ApprovalAppoint approvalAppoint;

    private String adetBefore;
    private String adetAfter;
    private String adetType;
    private int empCode;

    private AppointDetail(ApprovalAppoint approvalAppoint, String adetBefore, String adetAfter, String adetType, int empCode){
        this.approvalAppoint = approvalAppoint;
        this.adetBefore = adetBefore;
        this.adetAfter = adetAfter;
        this.adetType = adetType;
        this.empCode = empCode;
    }

    public static AppointDetail of(ApprovalAppoint approvalAppoint, String adetBefore, String adetAfter, String adetType, int empCode){
        return new AppointDetail(approvalAppoint, adetBefore, adetAfter, adetType, empCode);
    }
}
