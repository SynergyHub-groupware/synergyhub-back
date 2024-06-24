package synergyhubback.attendance.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import synergyhubback.attendance.domain.entity.DefaultSchedule;
import synergyhubback.employee.domain.entity.Employee;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface DefaultScheduleRepository extends JpaRepository<DefaultSchedule, Integer> {

    /* 모든 파라미터를 통한 지정 출퇴근시간 조회 */
    List<DefaultSchedule> findByDeptTitleAndAtdStartTimeAndAtdEndTime(String deptTitle, LocalTime startTime, LocalTime endTime);

    /* 부서이름을 통한 지정 출퇴근시간 조회 */
    List<DefaultSchedule> findAllByDeptTitle(String deptTitle);
    DefaultSchedule findByDeptTitle(String deptTitle);

    /* 부서이름과 사원코드를 통한 지정 출퇴근시간 조회 */
    DefaultSchedule findByDeptTitleAndEmployee(String deptTitle, Employee employee);
}
