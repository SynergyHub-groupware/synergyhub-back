package synergyhubback.employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.auth.dto.LoginDto;
import synergyhubback.common.exception.NotFoundException;
import synergyhubback.employee.domain.entity.*;
import synergyhubback.employee.domain.repository.*;
import synergyhubback.employee.dto.request.*;
import synergyhubback.employee.dto.response.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static synergyhubback.common.exception.type.ExceptionCode.*;


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
    private final DetailByEmpRegistRepository detailByEmpRegistRepository;


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

    public void empsRegist(List<EmployeeRegistRequest> employeeRegistRequests) {
        for (EmployeeRegistRequest employeeRegistRequest : employeeRegistRequests) {

            /* 사원코드 생성 (yyyyMM+1) */
            String hireYearMonth = employeeRegistRequest.getHire_date().format(DateTimeFormatter.ofPattern("yyyyMM"));
            long count = employeeRepository.countByHireYearMonth(hireYearMonth);
            String empCode = hireYearMonth + (count + 1);

            Department department = departmentRepository.findByDeptCode(employeeRegistRequest.getDept_code());
            Title title = titleRepository.findByTitleCode(employeeRegistRequest.getTitle_code());
            Position position = positionRepository.findByPositionCode(employeeRegistRequest.getPosition_code());

            /* Employee 객체 생성 */
            Employee newEmp = Employee.regist(
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

            /* DetailByEmpRegist 객체 생성 */
            DetailByEmpRegist detailByEmpRegist = new DetailByEmpRegist(
                    employeeRegistRequest.getDetailErdNum(),
                    employeeRegistRequest.getDetailErdTitle(),
                    newEmp
            );

            /* Employee 객체에 DetailByEmpRegist 추가 */
            newEmp.addEmpRegistDetail(detailByEmpRegist);

            /* 저장 */
            employeeRepository.save(newEmp);
        }
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


    public RecordCardResponse getTeamRecordCard(int logInEmpCode, int empCode) throws IllegalAccessException, NotFoundException {

        Employee logInEmployee = employeeRepository.findByEmpCode(logInEmpCode);

        Employee targetEmployee = employeeRepository.findByEmpCode(empCode);

        if (!isAuthorized(logInEmployee, targetEmployee)) {
            throw new IllegalAccessException("조회할 권한이 없습니다.");
        }

        List<SchoolInfo> schoolInfos = schoolInfoRepository.findAllByEmpCode(empCode);

        List<Certificate> certificates = certificateRepository.findAllByEmpCode(empCode);

        return RecordCardResponse.getRecordCard(targetEmployee, schoolInfos, certificates);

    }

    private boolean isAuthorized(Employee logInEmployee, Employee targetEmployee) {

        String logInEmpTitleCode = logInEmployee.getTitle().getTitle_code();

        if (!"T1".equals(logInEmpTitleCode) &&
                !"T2".equals(logInEmpTitleCode) &&
                !"T4".equals(logInEmpTitleCode) &&
                !"T5".equals(logInEmpTitleCode)) {
            return false;
        }

        String logInDeptCode = logInEmployee.getDepartment().getDept_code();
        String targetDeptCode = targetEmployee.getDepartment().getDept_code();

        if (logInDeptCode.equals(targetDeptCode)) {
            return true;
        }

        for (DeptRelations relation : logInEmployee.getDepartment().getSubDepartments()) {
            if (relation.getSubDepartment().getDept_code().equals(targetDeptCode)) {
                return true;
            }
        }
        return false;
    }



    public void registDept(RegistDeptRequest registDeptRequest) {

        String newDeptCode = nextDeptCode();

        Department department = new Department(
                newDeptCode,
                registDeptRequest.getDept_title(),
                LocalDate.now(),
                null,
                new HashSet<>(),
                new HashSet<>()
        );

        departmentRepository.save(department);
    }

    private String nextDeptCode() {

        List<Department> allDeptCodes = departmentRepository.findAll();

        int nextNumber = 1;
        for (Department dept : allDeptCodes) {
            String numberPart = dept.getDept_code().substring(1);
            try {
                int currentNumber = Integer.parseInt(numberPart);
                if (currentNumber >= nextNumber) {
                    nextNumber = currentNumber + 1;
                }
            } catch (NumberFormatException e) {

            }
        }

        return "D" + nextNumber;
    }

    public void registDeptRelations(RegistDeptRelationsRequest registDeptRelationsRequest) {

        Department parentDepartment = registDeptRelationsRequest.getParentDepartment();
        Department subDepartment = registDeptRelationsRequest.getSubDepartment();

        DeptRelations existingRelation = deptRelationsRepository.findByParentDepartmentAndSubDepartment(parentDepartment, subDepartment);

        if(existingRelation != null) {
            throw new IllegalArgumentException("이미 등록된 부서 관계 입니다.");
        }

        DeptRelations deptRelations = new DeptRelations(parentDepartment, subDepartment);

        deptRelationsRepository.save(deptRelations);
    }

    public void modifyDeptRelations(int dept_relations_code, Department parentDepartment, Department subDepartment) {

        DeptRelations deptRelations = deptRelationsRepository.findById(dept_relations_code)
                .orElseThrow(() -> new NotFoundException(DEPT_RELATIONS_NOT_FOUND));

        deptRelations.setParentDepartment(parentDepartment);
        deptRelations.setSubDepartment(subDepartment);

        deptRelationsRepository.save(deptRelations);

    }

    public void deleteDeptRelations(RegistDeptRelationsRequest registDeptRelationsRequest) {

        Department parentDepartment = registDeptRelationsRequest.getParentDepartment();

        Department subDepartment = registDeptRelationsRequest.getSubDepartment();

        DeptRelations deptRelations = deptRelationsRepository.findByParentDepartmentAndSubDepartment(parentDepartment, subDepartment);

        if (deptRelations != null) {

            deptRelationsRepository.delete(deptRelations);
        }

    }

    public List<GetDeptTitleResponse> getDepartments() {

        List<Department> getDepartments = departmentRepository.findAllAndRelations();

        return getDepartments.stream()
                .map(department -> new GetDeptTitleResponse(
                        department.getDept_code(),
                        department.getDept_title(),
                        department.getSubDepartments().stream()
                                .map(sub -> sub.getSubDepartment().getDept_title())
                                .collect(Collectors.toList()),
                        department.getParentDepartments().stream()
                                .map(par -> par.getParentDepartment().getDept_title())
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public DepartmentResponse getDeptDetailList(String dept_code) {

        Department department = departmentRepository.findDeptDetailByDeptCode(dept_code);

        String deptManagerName = getDeptManagerName(department);

        List<String> parDeptTitles = department.getParentDepartments().stream()
                .map(par -> par.getParentDepartment().getDept_title())
                .collect(Collectors.toList());

        List<String> subDeptTitles = department.getSubDepartments().stream()
                .map(sub -> sub.getSubDepartment().getDept_title())
                .collect(Collectors.toList());

        int numOfTeamMember = getNumOfTeamMembers(department);

        List<String> teamMemberNames = getTeamMemberNames(department);

        String parentDeptManagerName = deptRelationsRepository.findParentDepartmentManagerBySubDeptCode(department.getDept_code());

        DeptDetailResponse deptDetailResponse = DeptDetailResponse.getDeptDetail(
                department,
                deptManagerName,
                parDeptTitles,
                subDeptTitles,
                numOfTeamMember,
                teamMemberNames,
                parentDeptManagerName
        );

        return DepartmentResponse.from(List.of(deptDetailResponse));
    }

    private String getDeptManagerName(Department department) {
        return employeeRepository.findManagerByDeptCode(department.getDept_code());
    }

    private int getNumOfTeamMembers(Department department) {
        return employeeRepository.countTeamMembersByDeptCode(department.getDept_code());
    }

    private List<String> getTeamMemberNames(Department department) {
        return employeeRepository.findTeamMemberNamesByDeptCode(department.getDept_code());
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

    public void resetEmpPass(int emp_code) {

        Employee employee = employeeRepository.findByEmpCode(emp_code);

        String encodePassword = passwordEncoder.encode(String.valueOf(emp_code));

        employee.resetPassword(encodePassword);

        employeeRepository.save(employee);
    }


}
