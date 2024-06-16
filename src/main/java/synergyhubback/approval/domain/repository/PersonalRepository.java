package synergyhubback.approval.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synergyhubback.approval.domain.entity.Personal;

import java.util.Optional;

public interface PersonalRepository extends JpaRepository<Personal, String> {
    Optional<Personal> findTopByOrderByApCodeDesc();
}
