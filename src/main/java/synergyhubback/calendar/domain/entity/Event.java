package synergyhubback.calendar.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "EVENT")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Event {

    @Id
    @Column(name = "EVENT_CODE")
    private String id;

    @Column(name = "EVENT_TITLE")
    private String title;

    @Column(name = "EVENT_CON")
    private String eventCon;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "ALLDAY")
    private boolean allDay;

    @Column(name = "EVENT_GUESTS")
    private String eventGuests;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "EMP_CODE")
//    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LABEL_CODE")
    private Label label;

//    public static Event createEvent(
//            String title,
//            String eventCon,
//            LocalDateTime startDate,
//            LocalDateTime endDate,
//            boolean allDay,
//            String eventGuests,
//            Employee employee,
//            Label label
//    ) {
//        Event event = new Event();
//        event.setId("CA" + System.currentTimeMillis()); // 임시 ID 생성 로직
//        event.setTitle(title);
//        event.setEventCon(eventCon);
//        event.setStartDate(startDate);
//        event.setEndDate(endDate);
//        event.setAllDay(allDay);
//        event.setEventGuests(eventGuests);
//        event.setEmployee(employee);
//        event.setLabel(label);
//        return event;
//    }
}
