package synergyhubback.attendance.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synergyhubback.attendance.domain.entity.DefaultSchedule;

public interface OverTimeWorkRepository extends JpaRepository<DefaultSchedule, Integer> {
}
