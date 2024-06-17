package synergyhubback.approval.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import synergyhubback.approval.domain.entity.ApprovalAttendance;

import java.util.Optional;

public interface ApprovalAttendanceRepository extends JpaRepository<ApprovalAttendance, String> {

    @Query(value = "SELECT * FROM APPROVAL_ATTENDANCE " +
            "ORDER BY SUBSTRING(AATT_CODE, 1, 4), CAST(SUBSTRING(AATT_CODE, 5) AS UNSIGNED) DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<ApprovalAttendance> findTopOrderByAattCodeDesc();
}
