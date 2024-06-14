package synergyhubback.attendance.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "day_off")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class DayOff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int doCode;                //휴가코드(pk)
    private String doName;              //휴가명
    private LocalDate doStartDate;      //시작일자
    private LocalDate doEndDate;        //종료일자
    private LocalTime doStartTime;      //시작시간
    private LocalTime doEndTime;        //종료시간
    private LocalDate doInsertDate;     //부여일자
    private int granted;               //부여수
    private int used;                  //사용수
    private int remaining;             //잔여수
    private int empCode;               //사원코드 (추후 fk)

}
