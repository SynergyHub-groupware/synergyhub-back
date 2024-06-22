package synergyhubback.approval.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synergyhubback.approval.domain.entity.ApprovalStorage;

import java.util.List;

public interface ApprovalStorageRepository extends JpaRepository<ApprovalStorage, Integer> {
    List<ApprovalStorage> findByDocument_AdCode(String adCode);

    void deleteByDocument_AdCode(String adCode);
}