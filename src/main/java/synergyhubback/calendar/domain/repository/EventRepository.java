package synergyhubback.calendar.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synergyhubback.calendar.domain.entity.Event;

public interface EventRepository extends JpaRepository<Event, String> {
}
