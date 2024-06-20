package synergyhubback.employee.domain.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import synergyhubback.employee.domain.entity.Department;
import synergyhubback.employee.domain.entity.Employee;
import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


    // 이재현 로그인 관련 repository 로직 생성
    Optional<Employee> findByRefreshToken(String refreshToken);


    /* 입사년월로 사원코드 생성 */
    @Query("SELECT COUNT(e) FROM Employee e WHERE FUNCTION('DATE_FORMAT', e.hire_date, '%Y%m') = :hireYearMonth")
    long countByHireYearMonth(String hireYearMonth);


    /* 아이디로 내정보 조회, 인사기록카드 조회 */
    @Query("SELECT e FROM Employee e WHERE e.emp_code = :empCode")
    Employee findByEmpCode(int empCode);

    /* 팀원 정보 조회 */
    @Query("SELECT e FROM Employee e WHERE e.department.dept_code = :deptCode")
    List<Employee> findAllByDeptCode(@Param("deptCode") String deptCode);

    /* 사원코드로 부서코드 조회 */
    @Query("SELECT e.department.dept_code FROM Employee e WHERE e.emp_code = :empCode")
    String findDeptCodeByEmpCode(@Param("empCode") int empCode);

//    /* 팀원 정보 조회 */
//    @Query("SELECT e FROM Employee e WHERE e.department.dept_code = :deptCode")
//    List<Employee> findAllByDeptCode(String deptCode);
//
//    /* 사원코드로 부서코드 조회 */
//    @Query("SELECT e.department.dept_code FROM Employee e WHERE e.emp_code = :empCode")
//    String findDeptCodeByEmpCode(int empCode);

}
