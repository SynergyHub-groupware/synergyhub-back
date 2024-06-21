package synergyhubback.pheed.domain.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import synergyhubback.pheed.domain.entity.Pheed;

import java.util.List;

public interface PheedRepository extends JpaRepository<Pheed, Integer> {

    @Query("SELECT p FROM Pheed p WHERE p.employee.emp_code = :empCode")
    List<Pheed> findByEmployeeEmp_code(@Param("empCode") int empCode);

    @Query("SELECT p FROM Pheed p WHERE p.employee.emp_code = :empCode")
    List<Pheed> findAllByEmpCode(@Param("empCode") int empCode);
}