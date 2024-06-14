package synergyhubback.post.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comm_code")
    private int CommCode;
    @Column(name = "comm_con")

    private String CommCon;
    @Column(name = "comm_date")
    private LocalDateTime CommDate;
    @Column(name = "comm_status")

    private char CommStatus;

    @ManyToOne
    @JoinColumn(name = "Post_Code")
    private PostEntity PostCode;
    private int EmpCode;

}
