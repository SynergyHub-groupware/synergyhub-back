package synergyhubback.calendar.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import synergyhubback.calendar.domain.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
