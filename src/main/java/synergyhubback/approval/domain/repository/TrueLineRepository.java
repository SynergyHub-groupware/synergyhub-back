package synergyhubback.approval.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synergyhubback.approval.domain.entity.TrueLine;

public interface TrueLineRepository extends JpaRepository<TrueLine, Integer> {
}
