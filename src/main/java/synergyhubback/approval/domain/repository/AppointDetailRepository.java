package synergyhubback.approval.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synergyhubback.approval.domain.entity.AppointDetail;

public interface AppointDetailRepository extends JpaRepository<AppointDetail, Integer> {
}
