package synergyhubback.employee.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import synergyhubback.employee.domain.entity.Department;
import synergyhubback.employee.domain.entity.DeptRelations;

import java.util.List;


public interface DeptRelationsRepository extends JpaRepository<DeptRelations, Integer> {

    /* 부서 코드로 관계 조회 */
//    @Query("SELECT dr FROM DeptRelations dr WHERE dr.sub_dept_code = :deptCode or dr.sup_dept_code = :deptCode")
    List<DeptRelations> findAll();

}
