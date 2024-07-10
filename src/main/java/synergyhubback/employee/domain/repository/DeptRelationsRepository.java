package synergyhubback.employee.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import synergyhubback.employee.domain.entity.Department;
import synergyhubback.employee.domain.entity.DeptRelations;

import java.util.List;
import java.util.Optional;


public interface DeptRelationsRepository extends JpaRepository<DeptRelations, Integer> {

    /* 부서 코드로 관계 조회 */
//    @Query("SELECT dr FROM DeptRelations dr WHERE dr.sub_dept_code = :deptCode or dr.sup_dept_code = :deptCode")
    List<DeptRelations> findAll();

    /* 상위 부서 부서장 조회 */
    @Query("SELECT DISTINCT e.emp_name FROM Employee e JOIN e.department d WHERE d.dept_code = " +
            "(SELECT dr.parentDepartment.dept_code FROM DeptRelations dr WHERE dr.subDepartment.dept_code = :subDeptCode) " +
            "AND e.title.title_code = 'T4' AND e.emp_status = 'Y'")
    String findParentDepartmentManagerBySubDeptCode(String subDeptCode);

    DeptRelations findByParentDepartmentAndSubDepartment(Department parentDepartment, Department subDepartment);

}
