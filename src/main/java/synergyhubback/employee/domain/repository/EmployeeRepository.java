package synergyhubback.employee.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import synergyhubback.employee.domain.entity.Employee;

import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // 이재현 로그인 관련 repository 로직 생성
    @Query("SELECT e FROM Employee e WHERE e.emp_code = :empCode")
    Optional<Employee> findByEmpCode(int empCode);

    // 이재현 로그인 관련 repository 로직 생성
    Optional<Employee> findByRefreshToken(String refreshToken);

    /* 입사년월로 사원코드 생성 */
    @Query("SELECT COUNT(e) FROM Employee e WHERE FUNCTION('DATE_FORMAT', e.hire_date, '%Y%m') = :hireYearMonth")
    long countByHireYearMonth(String hireYearMonth);

}
