package synergyhubback.employee.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import synergyhubback.employee.domain.entity.DetailByEmpRegist;
import synergyhubback.employee.domain.entity.Title;


public interface DetailByEmpRegistRepository extends JpaRepository<DetailByEmpRegist, Integer> {


}
