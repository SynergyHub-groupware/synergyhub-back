package synergyhubback.attendance.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import synergyhubback.attendance.domain.entity.AttendanceStatus;
import synergyhubback.attendance.domain.entity.DayOff;
import synergyhubback.attendance.domain.entity.DayOffBalance;

import java.time.LocalDate;
import java.util.List;

public interface DayOffRepository extends JpaRepository<DayOff, Integer> {

    @Query("SELECT a FROM DayOff a WHERE a.employee.emp_code = :empCode")
    List<DayOff> findAllByEmpCode(int empCode);

    @Query("SELECT a FROM DayOff a WHERE a.employee.emp_code = :empCode AND a.doStartDate = :doStartDate")
    DayOff findByEmpCode(int empCode, String doStartDate);

    @Query("SELECT a FROM DayOffBalance a WHERE a.employee.emp_code = :empCode")
    DayOffBalance findMyDayOffBalanceByEmpCode(int empCode);

    @Query("SELECT a FROM DayOff a WHERE a.doStartDate = :doStartDate OR a.doEndDate = :doEndDate")
    List<DayOff> findDoByStartDateOrDoEndDate(LocalDate doStartDate, LocalDate doEndDate);
}
