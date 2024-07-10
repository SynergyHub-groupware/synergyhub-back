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
    @JoinColumn(name = "rev_stor", referencedColumnName = "storCode")
    private Storage revStor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_stor", referencedColumnName = "storCode")
    private Storage sendStor;

    public void setEmpRev(Employee empRev) {this.empRev = empRev;}
    public void setEmpSend(Employee empSend) {this.empSend = empSend;}
    public void setRevStor(Storage revStor) {
        this.revStor = revStor;
    }
    public void setSendStor(Storage sendStor) { this.sendStor = sendStor;}

    public Message(String msgCode, LocalDate sendDate, String msgTitle, String msgCon, String msgStatus, String emerStatus){
        this.msgCode = msgCode;
        this.sendDate = sendDate;
        this.msgTitle = msgTitle;
        this.msgCon = msgCon;
        this.msgStatus = msgStatus;
        this.emerStatus = emerStatus;
    }

    public static Message create(String msgCode, LocalDate sendDate, String msgTitle, String msgCon, String msgStatus, String emerStatus) {

        return new Message(
                msgCode,
                sendDate,
                msgTitle,
                msgCon,
                msgStatus,
                emerStatus
        );
    }
}
