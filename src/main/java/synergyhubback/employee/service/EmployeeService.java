package synergyhubback.employee.service;

import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.employee.domain.repository.EmployeeRepository;


import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

//    public void empRegist(EmployeeRegistRequest employeeRegistRequest) {
//
////        String hireYearMonth = new SimpleDateFormat("yyyyMM").format(employeeRegistRequest.getHire_date());
////        long count = employeeRepository.countByHireYearMonth(hireYearMonth);
////        int empCode = Integer.parseInt(hireYearMonth + (count + 1));
//
//        final Employee newEmp = Employee.regist(
//
//                employeeRegistRequest.getEmp_code(),
//                employeeRegistRequest.getEmp_name(),
////                Integer.parseInt(passwordEncoder.encode(String.valueOf(empCode))),        // 사원코드 비밀번호로 사용
//                employeeRegistRequest.getEmp_pass(),
//                employeeRegistRequest.getSocial_security_no(),
//                employeeRegistRequest.getDept_code(),
//                employeeRegistRequest.getPosition_code(),
//                employeeRegistRequest.getTitle_code(),
//                employeeRegistRequest.getHire_date(),
//                employeeRegistRequest.getEmp_status()
//        );
//
//        employeeRepository.save(newEmp);
//
//    }

//    public MyInfoResponse getMyInfo(int emp_code) {
//
//        Employee employee = employeeRepository.findByEmpCode(emp_code);
//
//        return MyInfoResponse.getMyInfo(employee);
//
//    }

//    public RecordCardResponse getRecordCard(int emp_code) {
//
//        Employee employee = employeeRepository.findByEmpCode(emp_code);
//
//        List<SchoolInfo> schoolInfos = schoolInfoRepository.findAllByEmpCode(emp_code);
//
//        List<Certificate> certificates = certificateRepository.findAllByEmpCode(emp_code);
//
//
//        return RecordCardResponse.getRecordCard(employee, schoolInfos, certificates);
//    }
//
//    public EmployeeListResponse getEmployeeList(int emp_code) {
//
//        String dept_code = employeeRepository.findDeptCodeByEmpCode(emp_code);
//
//        List<Employee> employees = employeeRepository.findAllByDeptCode(dept_code);
//
//
//        return EmployeeListResponse.getEmployeeList(employees);
//    }
}
