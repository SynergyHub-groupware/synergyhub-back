package synergyhubback.approval.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import synergyhubback.approval.domain.entity.Personal;

import java.util.Optional;

public interface PersonalRepository extends JpaRepository<Personal, String> {
    @Query(value = "SELECT * FROM APPROVAL_PERSONAL " +
            "ORDER BY SUBSTRING(AP_CODE, 1, 2), CAST(SUBSTRING(AP_CODE, 3) AS UNSIGNED) DESC " +
            "LIMIT 1", nativeQuery = true)

    Optional<Personal> findTopOrderByApCodeDesc();
}
