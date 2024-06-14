package synergyhubback.post.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import synergyhubback.post.domain.type.PostCommSet;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class PostEntity {
    @Id
    @Column(name = "post_code")
    private String PostCode;
    @Column(name = "post_name")
    private String PostName;
    @Column(name = "post_view_cnt")
    private int PostViewCnt;
    @Column(name = "post_date")
    private LocalDate PostDate;
    @Column(name = "post_con")
    private String PostCon;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "post_comm_set", columnDefinition = "int")
    private PostCommSet postCommSet;
    @Column(name = "fix_status")
    private char FixStatus;
    @Column(name = "notice_status")
    private char NoticeStatus;


    private int EmpCode;
    @OneToOne
    @JoinColumn(name = "Low_Code")
    private LowBoardEntity LowBoardCode;
    @OneToOne
    @JoinColumn(name = "Ps_Code")
    private PostSortEntity PsCode;
}
