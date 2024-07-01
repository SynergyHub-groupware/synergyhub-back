package synergyhubback.attendance.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synergyhubback.attendance.domain.entity.AttendanceStatus;
import synergyhubback.attendance.domain.entity.DayOffBalance;

public interface DayOffBalanceRepository extends JpaRepository<DayOffBalance, Integer> {
    DayOffBalance findTopByOrderByDbCodeDesc();
}
