package synergyhubback.approval.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synergyhubback.approval.domain.entity.ApprovalBox;

public interface ApprovalBoxRepository extends JpaRepository<ApprovalBox, Integer> {
}
