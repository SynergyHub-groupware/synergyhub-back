package synergyhubback.attendance.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import synergyhubback.attendance.domain.entity.AttendanceStatus;
import synergyhubback.attendance.domain.entity.DayOffBalance;

import java.util.List;

public interface DayOffBalanceRepository extends JpaRepository<DayOffBalance, Integer> {
    DayOffBalance findTopByOrderByDbCodeDesc();

    @Query("SELECT a FROM DayOffBalance a WHERE a.employee.emp_code = :empCode")
    DayOffBalance findAllByEmpCode(int empCode);
}
