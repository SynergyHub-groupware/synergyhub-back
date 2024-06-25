package synergyhubback.attendance.domain.repository;

import org.apache.ibatis.annotations.Param;
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
    List<DefaultSchedule> findByDeptCodeAndAtdStartTimeAndAtdEndTime(String deptCode, LocalTime startTime, LocalTime endTime);

    /* 부서이름을 통한 지정 출퇴근시간 조회 */
    List<DefaultSchedule> findAllByDeptCode(String deptCode);

    /* 부서이름과 사원코드를 통한 지정 출퇴근시간 조회 */
    DefaultSchedule findByDeptCodeAndEmployee(String deptCode, Employee employee);

    /* 사원정보를 통한 지정 출퇴근시간 조회 */
    DefaultSchedule findByEmployee(Employee employee);

    /* 사원정보가 없고, 부서정보가 있는 지정 출퇴근시간 조회 */
    @Query("SELECT ds FROM DefaultSchedule ds WHERE ds.deptCode = :deptCode AND ds.employee IS NULL")
    DefaultSchedule findWithDeptCodeAndNullEmployee(@Param("deptCode") String deptCode);
}
