package synergyhubback.employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.auth.dto.LoginDto;
import synergyhubback.common.exception.NotFoundException;
import synergyhubback.employee.domain.entity.Employee;
import synergyhubback.employee.domain.repository.EmployeeRepository;
import synergyhubback.employee.dto.request.EmployeeRegistRequest;

import java.time.format.DateTimeFormatter;

import static synergyhubback.common.exception.type.ExceptionCode.NOT_FOUND_REFRESH_TOKEN;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public LoginDto findByEmpCode(int emp_code) {

        Employee employee = employeeRepository.findByEmpCode(emp_code)
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디가 존재하지 않습니다."));

        return LoginDto.from(employee);
    }

    public void updateRefreshToken(int emp_code, String refreshToken) {
        Employee employee = employeeRepository.findByEmpCode(emp_code)
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디가 존재하지 않습니다."));
        System.out.println("refresh Token 발급");

        employee.updateRefreshToken(refreshToken);
    }

    @Transactional(readOnly = true)
    public LoginDto findByRefreshToken(String refreshToken) {

        Employee employee = employeeRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_REFRESH_TOKEN));

        return LoginDto.from(employee);
    }

    public void empRegist(EmployeeRegistRequest employeeRegistRequest) {

        String hireYearMonth = employeeRegistRequest.getHire_date().format(DateTimeFormatter.ofPattern("yyyyMM"));
        long count = employeeRepository.countByHireYearMonth(hireYearMonth);
        String empCode = hireYearMonth + (count + 1);

        final Employee newEmp = Employee.regist(

                employeeRegistRequest.getEmp_code(),
                employeeRegistRequest.getEmp_name(),
                passwordEncoder.encode(empCode),
                employeeRegistRequest.getSocial_security_no(),
                employeeRegistRequest.getDept_code(),
                employeeRegistRequest.getPosition_code(),
                employeeRegistRequest.getTitle_code(),
                employeeRegistRequest.getHire_date(),
                employeeRegistRequest.getEmp_status()
        );

        employeeRepository.save(newEmp);

    }


}
