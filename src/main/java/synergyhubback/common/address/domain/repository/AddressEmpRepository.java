package synergyhubback.common.address.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synergyhubback.employee.domain.entity.Employee;


public interface AddressEmpRepository extends JpaRepository<Employee, Integer> {
}
