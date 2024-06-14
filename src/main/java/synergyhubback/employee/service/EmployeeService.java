package synergyhubback.employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.employee.domain.entity.*;
import synergyhubback.employee.domain.repository.*;
import synergyhubback.employee.dto.request.EmployeeRegistRequest;
import synergyhubback.employee.dto.response.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final CertificateRepository certificateRepository;
    private final SchoolInfoRepository schoolInfoRepository;
    private final DepartmentRepository departmentRepository;
    private final DeptRelationsRepository deptRelationsRepository;

    // 이재현 로그인 관련 service 로직 생성
    @Transactional(readOnly = true)
    public LoginDto findByEmpCode(int emp_code) {

        Employee employee = employeeRepository.findByEmpCode(emp_code)
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디가 존재하지 않습니다."));

        return LoginDto.from(employee);
    }

    // 이재현 로그인 관련 service 로직 생성
    public void updateRefreshToken(int emp_code, String refreshToken) {
        Employee employee = employeeRepository.findByEmpCode(emp_code)
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디가 존재하지 않습니다."));
        System.out.println("refresh Token 발급");

        employee.updateRefreshToken(refreshToken);
    }

    // 이재현 로그인 관련 service 로직 생성
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

//    private final PasswordEncoder passwordEncoder;

    public void empRegist(EmployeeRegistRequest employeeRegistRequest) {

//        String hireYearMonth = new SimpleDateFormat("yyyyMM").format(employeeRegistRequest.getHire_date());
//        long count = employeeRepository.countByHireYearMonth(hireYearMonth);
//        int empCode = Integer.parseInt(hireYearMonth + (count + 1));


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

    public MyInfoResponse getMyInfo(int emp_code) {

        Employee employee = employeeRepository.findByEmpCode(emp_code);

        return MyInfoResponse.getMyInfo(employee);

    }

    public RecordCardResponse getRecordCard(int emp_code) {

        Employee employee = employeeRepository.findByEmpCode(emp_code);

        List<SchoolInfo> schoolInfos = schoolInfoRepository.findAllByEmpCode(emp_code);

        List<Certificate> certificates = certificateRepository.findAllByEmpCode(emp_code);


        return RecordCardResponse.getRecordCard(employee, schoolInfos, certificates);
    }

    public EmployeeListResponse getEmployeeList(int emp_code) {

        String dept_code = employeeRepository.findDeptCodeByEmpCode(emp_code);

        List<Employee> employees = employeeRepository.findAllByDeptCode(dept_code);

        List<EmployeeResponse> employeeResponses = employees.stream()
                .map(employee -> {
                    EmployeeResponse response = new EmployeeResponse(
                            employee.getEmp_code(),
                            employee.getEmp_name(),
                            null,
                            null,
                            employee.getPhone(),
                            employee.getHire_date(),
                            employee.getSocial_security_no()
                    );
                    response.setDept_title(employee.getDepartment().getDept_title());
                    response.setPosition_name(employee.getPosition().getPosition_name());
                    return response;
                })
                .collect(Collectors.toList());

        return EmployeeListResponse.getEmployeeList(employees);
    }

    public List<GetDeptTitleResponse> getDepartments() {

        List<Department> getDepartments = departmentRepository.findAll();

        return getDepartments.stream()
                .map(GetDeptTitleResponse::getDeptTitle)
                .collect(Collectors.toList());
    }

    public DepartmentResponse getDepartmentList(String dept_code) {

        List<Department> departmentList = departmentRepository.findAll();
        List<DeptRelations> relations = deptRelationsRepository.findAll();
        List<Employee> employees = employeeRepository.findAll();


        List<Employee> statusEmployee = employees.stream()
                .filter(employee -> "Y".equals(employee.getEmp_status()))
                .collect(Collectors.toList());

        Map<String, String> deptCodeToTitle = departmentList.stream()
                .collect(Collectors.toMap(Department::getDept_code, Department::getDept_title));

        Map<String, List<String>> supDeptRelations = relations.stream()
                .collect(Collectors.groupingBy(
                        DeptRelations::getSub_dept_code,
                        Collectors.mapping(DeptRelations::getSup_dept_code, Collectors.toList())
                ));

        Map<String, List<String>> subDeptRelations = relations.stream()
                .collect(Collectors.groupingBy(
                        DeptRelations::getSup_dept_code,
                        Collectors.mapping(DeptRelations::getSub_dept_code, Collectors.toList())
                ));

        Map<String, Employee> deptManagers = statusEmployee.stream()
                .filter(employee -> "T4".equals(employee.getTitle().getTitle_code()))
                .collect(Collectors.toMap(
                        employee -> employee.getDepartment().getDept_code(),
                        employee -> employee,
                        (existing, replacement) -> existing
                ));

        Map<String, List<Employee>> employeesByDept = statusEmployee.stream()
                .collect(Collectors.groupingBy(employee -> employee.getDepartment().getDept_code()));

        List<DeptDetailResponse> deptDetails = departmentList.stream()
                .map(department -> {
                    Employee deptManager = deptManagers.get(department.getDept_code());
                    String deptManagerName = deptManager != null ? deptManager.getEmp_name() : null;
                    List<Employee> deptEmployees = employeesByDept.getOrDefault(department.getDept_code(), List.of());
                    int numOfTeamMember = deptEmployees.size();
                    List<String> teamMemberNames = deptEmployees.stream()
                            .map(Employee::getEmp_name)
                            .collect(Collectors.toList());

                    List<String> supDeptTitle = supDeptRelations.getOrDefault(department.getDept_code(), List.of()).stream()
                            .map(deptCodeToTitle::get)
                            .collect(Collectors.toList());

                    List<String> subDeptTitle = subDeptRelations.getOrDefault(department.getDept_code(), List.of()).stream()
                            .map(deptCodeToTitle::get)
                            .collect(Collectors.toList());

                    return DeptDetailResponse.getDeptDetail(department, deptManagerName, supDeptTitle, subDeptTitle, numOfTeamMember, teamMemberNames);

                }).collect(Collectors.toList());

        return new DepartmentResponse(deptDetails);

    }

    public List<OrgResponse> getOrgEmps() {

        List<Employee> getOrgEmps = employeeRepository.findAll();

        return getOrgEmps.stream()
                .map(OrgResponse::getOrg)
                .collect(Collectors.toList());
    }

    public OrgDetailResponse getOrgEmpDetail(int emp_code) {

        Employee employee = employeeRepository.findByEmpCode(emp_code);

        return OrgDetailResponse.getOrgDetail(employee);
    }

}
