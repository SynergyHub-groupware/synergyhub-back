package synergyhubback.post.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "post_role")
public class PostRoleEntity {
    @Id
    @Column(name = "pr_code")
    private int PrCode;
    @Column(name = "pr_write_role")
    private char PrWriteRole;
    @OneToOne
    @JoinColumn(name = "Low_Code")
    private LowBoardEntity LowCode;
//    @OneToOne
//    private Employee EmpCode;
    @Column(name = "pr_admin")
    private char PrAdmin;

}
