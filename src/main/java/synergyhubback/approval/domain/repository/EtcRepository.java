package synergyhubback.approval.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synergyhubback.approval.domain.entity.Etc;

import java.util.Optional;

public interface EtcRepository extends JpaRepository<Etc, String> {
    Optional<Etc> findTopByOrderByAeCodeDesc();
}
