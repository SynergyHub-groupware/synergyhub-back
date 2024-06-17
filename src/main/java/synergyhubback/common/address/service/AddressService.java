package synergyhubback.common.address.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.common.address.domain.dto.AddressSelect;
import synergyhubback.common.address.domain.repository.AddressDeptRepository;
import synergyhubback.common.address.domain.repository.AddressEmpRepository;
import synergyhubback.common.address.domain.repository.AddressPositionRepository;
import synergyhubback.employee.domain.entity.Department;
import synergyhubback.employee.domain.entity.Employee;
import synergyhubback.employee.domain.entity.Position;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {

    private final AddressDeptRepository addressDeptRepository;
    private final AddressPositionRepository addressPositionRepository;
    private final AddressEmpRepository addressEmpRepository;
    public List<AddressSelect> getAllAddress() {

        List<Employee> employees = addressEmpRepository.findAll();

        return employees.stream().map(employee -> {

            Department department = addressDeptRepository.findById(employee.getDepartment().getDept_code()).orElse(null);
            Position position = addressPositionRepository.findById(employee.getPosition().getPosition_code()).orElse(null);

            return AddressSelect.getAddressSelect(employee, department, position);
        }).collect(Collectors.toList());
    }
}
