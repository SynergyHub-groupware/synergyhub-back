package synergyhubback.pheed.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import synergyhubback.pheed.domain.entity.Pheed;

@Repository
public interface PheedRepository  extends JpaRepository<Pheed, Long> {

}
