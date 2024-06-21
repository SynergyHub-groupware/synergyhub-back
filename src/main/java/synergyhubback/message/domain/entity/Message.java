package synergyhubback.message.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import synergyhubback.employee.domain.entity.Employee;

import java.time.LocalDate;

@Entity
@Table(name = "message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Message {

    @Id
    private String msgCode;
    private LocalDate sendDate;
    private String msgTitle;
    private String msgCon;
    private String msgStatus;
    private String emerStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMP_REV", referencedColumnName = "emp_code")
    private Employee empRev;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMP_SEND", referencedColumnName = "emp_code")
    private Employee empSend;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stor_code", referencedColumnName = "storCode")
    private Storage storCode;

}
