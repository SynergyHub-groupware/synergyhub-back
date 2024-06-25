package synergyhubback.attendance.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import synergyhubback.employee.domain.entity.Employee;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "dayoff")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class DayOff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int doCode;                     //휴가기록코드(pk)
    private String doName;                  //휴가명
    private int doUsed;                     //신청일수
    private LocalDate doStartDate;          //시작일자
    private LocalDate doEndDate;            //종료일자
    private LocalTime doStartTime;          //시작시간
    private LocalTime doEndTime;            //종료시간

    @ManyToOne
    @JoinColumn(name = "db_code")
    private DayOffBalance dayOffBalance;    //휴가관리코드

}
