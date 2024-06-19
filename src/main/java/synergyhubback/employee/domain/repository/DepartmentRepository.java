package synergyhubback.employee.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import synergyhubback.employee.domain.entity.Certificate;
import synergyhubback.employee.domain.entity.Department;

import java.util.List;


public interface DepartmentRepository extends JpaRepository<Department, String> {

    @Query("SELECT d FROM Department d WHERE d.dept_code = :deptCode")
    Department findByDeptCode(String deptCode);

    /* 전체 부서 조회 */

    List<Department> findAll();

}
