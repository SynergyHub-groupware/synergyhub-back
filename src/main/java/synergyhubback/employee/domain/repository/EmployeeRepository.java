package synergyhubback.employee.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import synergyhubback.employee.domain.entity.Certificate;
import synergyhubback.employee.domain.entity.Employee;
import synergyhubback.employee.domain.entity.SchoolInfo;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    /* 입사년월로 사원코드 생성 */
    @Query("SELECT COUNT(e) FROM Employee e WHERE FUNCTION('DATE_FORMAT', e.hire_date, '%Y%m') = :hireYearMonth")
    long countByHireYearMonth(String hireYearMonth);

    /* 아이디로 내정보 조회 */
    @Query("SELECT e FROM Employee e WHERE e.emp_code = :empCode")
    Employee findByEmpCode(int empCode);

    @Query("SELECT e FROM Employee e WHERE e.emp_code = :empCode")
    Employee findById(int empCode);
}
