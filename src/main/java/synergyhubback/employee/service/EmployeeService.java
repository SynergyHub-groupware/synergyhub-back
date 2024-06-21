package synergyhubback.employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.auth.dto.LoginDto;
import synergyhubback.common.exception.NotFoundException;
import synergyhubback.employee.domain.entity.*;
import synergyhubback.employee.domain.repository.*;
import synergyhubback.employee.dto.request.EmployeeRegistRequest;
import synergyhubback.employee.dto.response.*;
import synergyhubback.post.service.PostService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static synergyhubback.common.exception.type.ExceptionCode.NOT_FOUND_REFRESH_TOKEN;


@Service("employeeService")
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final CertificateRepository certificateRepository;
    private final SchoolInfoRepository schoolInfoRepository;
    private final DepartmentRepository departmentRepository;
    private final DeptRelationsRepository deptRelationsRepository;
    private final TitleRepository titleRepository;
    private final PositionRepository positionRepository;

    @Transactional(readOnly = true)
    public LoginDto findByEmpCode(int emp_code) {

        try {

            Employee employee = employeeRepository.findByEmpCode(emp_code);
            return LoginDto.from(employee);

        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            System.out.println("해당 아이디가 존재하지 않습니다.");
        }

        return null;
    }

    // 이재현 로그인 관련 service 로직 생성
    public void updateRefreshToken(int emp_code, String refreshToken) {
        try {

            Employee employee = employeeRepository.findByEmpCode(emp_code);
            employee.updateRefreshToken(refreshToken);
        } catch ( Exception e) {
            System.out.println(e);
        }

        System.out.println("refresh Token 발급");

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

        Department department = departmentRepository.findByDeptCode(employeeRegistRequest.getDept_code());
        Title title = titleRepository.findByTitleCode(employeeRegistRequest.getTitle_code());
        Position position = positionRepository.findByPositionCode(employeeRegistRequest.getPosition_code());

        final Employee newEmp = Employee.regist(

                Integer.parseInt(empCode),
                employeeRegistRequest.getEmp_name(),
                passwordEncoder.encode(empCode),
                employeeRegistRequest.getSocial_security_no(),
                employeeRegistRequest.getHire_date(),
                employeeRegistRequest.getEmp_status()
        );

        newEmp.setDepartment(department);
        newEmp.setTitle(title);
        newEmp.setPosition(position);

        employeeRepository.save(newEmp);

    }

    public MyInfoResponse getMyInfo(int empCode) {


        Employee employee = employeeRepository.findByEmpCode(empCode);

        return MyInfoResponse.getMyInfo(employee);

    }

    public RecordCardResponse getRecordCard(int empCode) {

        Employee employee = employeeRepository.findByEmpCode(empCode);

        List<SchoolInfo> schoolInfos = schoolInfoRepository.findAllByEmpCode(empCode);

        List<Certificate> certificates = certificateRepository.findAllByEmpCode(empCode);


        return RecordCardResponse.getRecordCard(employee, schoolInfos, certificates);
    }

    public EmployeeListResponse employeeList(int empCode) {

        System.out.println("findDeptCodeByEmpCode : " + empCode);
        String dept_code = employeeRepository.findDeptCodeByEmpCode(empCode);

        System.out.println("dept code : " + dept_code);
        List<Employee> employees = employeeRepository.findAllByDeptCode(dept_code);

        System.out.println("emp found : " + employees.size());

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

    public List<OrgResponse> getOrgEmps(


    ) {

        List<Employee> getOrgEmps = employeeRepository.findAll();

        return getOrgEmps.stream()
                .map(OrgResponse::getOrg)
                .collect(Collectors.toList());
    }

    public OrgDetailResponse getOrgEmpDetail(int emp_code) {

        Employee employee = employeeRepository.findByEmpCode(emp_code);

        return OrgDetailResponse.getOrgDetail(employee);
    }


    public void resetEmpPass(int empCode) {

        Employee employee = employeeRepository.findById(empCode)
                .orElseThrow(() -> new IllegalArgumentException("사원번호 노 유효"));

        String encodePassword = passwordEncoder.encode(String.valueOf(empCode));

        employee.resetPassword(encodePassword);

        employeeRepository.save(employee);
    }

}
