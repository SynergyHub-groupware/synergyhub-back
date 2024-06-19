package synergyhubback.calendar.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synergyhubback.employee.domain.entity.Employee;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "EVENT")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private String id;

    @Column(name = "TITLE")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMP_CODE")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LABEL_CODE")
    private Label label;

    // 정적 팩토리 메서드
    public static Event createEvent(String title, String eventCon, LocalDateTime startDate, LocalDateTime endDate, boolean allDay, String eventGuests, Employee employee, Label label) {
        Event event = new Event();
        event.setTitle(title);
        event.setEventCon(eventCon);
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        event.setAllDay(allDay);
        event.setEventGuests(eventGuests);
        event.setEmployee(employee);
        event.setLabel(label);
        return event;
    }
}
