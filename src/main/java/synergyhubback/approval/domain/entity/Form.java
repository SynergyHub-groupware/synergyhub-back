package synergyhubback.approval.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Clob;

@Entity
@Table(name = "APPROVAL_FORM")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int afCode;
    private String afName;
    private String afExplain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lsCode")
    private LineSort lineSort;

    @Column(columnDefinition = "LONGTEXT")
    private String afCon;
}
