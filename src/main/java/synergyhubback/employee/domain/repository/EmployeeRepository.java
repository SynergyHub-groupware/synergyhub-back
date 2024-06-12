package synergyhubback.employee.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import synergyhubback.employee.domain.entity.Employee;

import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


    @Query("SELECT e FROM Employee e WHERE e.emp_code = :empCode")
    Optional<Employee> findByEmpCode(int empCode);

    Optional<Employee> findByRefreshToken(String refreshToken);
}
