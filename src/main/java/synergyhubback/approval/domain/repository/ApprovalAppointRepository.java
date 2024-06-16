package synergyhubback.approval.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synergyhubback.approval.domain.entity.ApprovalAppoint;

import java.util.Optional;

public interface ApprovalAppointRepository extends JpaRepository<ApprovalAppoint, String> {
    Optional<ApprovalAppoint> findTopByOrderByAappCodeDesc();
}
