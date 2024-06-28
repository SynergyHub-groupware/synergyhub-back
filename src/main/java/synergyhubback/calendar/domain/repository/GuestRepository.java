package synergyhubback.calendar.domain.repository;

import org.springframework.data.jpa.repository.Query;
import synergyhubback.calendar.domain.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, String> {
    @Query(value = "SELECT * FROM GUEST ORDER BY CAST(SUBSTRING(GUEST_CODE, 3) AS UNSIGNED) DESC LIMIT 1", nativeQuery = true)
    Optional<Guest> findTopByOrderByGuestCodeDesc();
}
