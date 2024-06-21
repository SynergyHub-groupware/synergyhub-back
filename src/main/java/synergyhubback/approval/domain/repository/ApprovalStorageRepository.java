package synergyhubback.approval.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synergyhubback.approval.domain.entity.ApprovalStorage;

public interface ApprovalStorageRepository extends JpaRepository<ApprovalStorage, Integer> {
}
