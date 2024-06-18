package synergyhubback.attendance.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalTime;

//@Entity
////@Table(name = "overtime_work")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//@EntityListeners(AuditingEntityListener.class)
//public class OvertimeWork {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int owCode;            //초과근무코드(pk)
//    private LocalTime startTime;    //시작시간
//    private LocalTime endTime;      //종료시간
//    private int empCode;           //사원코드 (추후 fk)
//
//}
