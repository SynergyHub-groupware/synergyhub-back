package synergyhubback.attendance.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import synergyhubback.attendance.domain.entity.Attendance;
import synergyhubback.attendance.dto.response.AttendancesResponse;
import synergyhubback.employee.domain.entity.Employee;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    /* 나의 근태 기록 조회 */

    //1. 금주의 근태 기록

    @Query("SELECT a FROM Attendance a WHERE a.atdDate BETWEEN :startDate AND :endDate")
    List<AttendancesResponse> findAttendanceInDateRange(@Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate);

    /* 가장 최신 근태 기록 조회 */
    Attendance findTopByOrderByAtdCodeDesc();

    Attendance findByEmployeeAndAtdDate(Employee employee, LocalDate currentDate);

    /* 오늘의 근태 기록 조회 */
    @Query("SELECT a FROM Attendance a WHERE DATE(a.startTime) = :date")
    Attendance findByStartDate(@Param("date") LocalDate date);




    /* 상세 근태 기록 조회 : 직급에 따라 조회할 수 있는 범위 제한 */

    /* 나의 초과근무 기록 조회 */

    /* 나의 외근 기록 조회 */

    /* 나의 출장 기록 조회 */




}
