package synergyhubback.approval.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synergyhubback.approval.domain.entity.ApprovalAttendance;

import java.util.Optional;

public interface ApprovalAttendanceRepository extends JpaRepository<ApprovalAttendance, String> {
    Optional<ApprovalAttendance> findTopByOrderByAattCodeDesc();
}
