package synergyhubback.calendar.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import synergyhubback.calendar.domain.entity.Event;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

    // 가장 최근의 이벤트 코드를 찾는 쿼리
    @Query(value = "SELECT * FROM EVENT ORDER BY CAST(SUBSTRING(EVENT_CODE, 3) AS UNSIGNED) DESC LIMIT 1", nativeQuery = true)
    Optional<Event> findTopByOrderByIdDesc();
}
