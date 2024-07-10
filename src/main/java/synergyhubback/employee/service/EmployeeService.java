package synergyhubback.employee.service;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.approval.domain.entity.AppointDetail;
import synergyhubback.approval.domain.entity.ApprovalAppoint;
import synergyhubback.approval.domain.repository.AppointDetailRepository;
import synergyhubback.approval.domain.repository.ApprovalAppointRepository;
import synergyhubback.attendance.dto.response.AttendancesResponse;
import synergyhubback.auth.dto.LoginDto;
import synergyhubback.common.address.service.EmailService;
import synergyhubback.common.exception.NotFoundException;
import synergyhubback.employee.domain.entity.*;
import synergyhubback.employee.domain.repository.*;
import synergyhubback.employee.dto.request.*;
import synergyhubback.employee.dto.response.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static synergyhubback.common.exception.type.ExceptionCode.*;
import static synergyhubback.employee.dto.response.EmployeeListResponse.getEmployeeList;


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
    private final EmailService emailService;
    private final AppointDetailRepository appointDetailRepository;
    private final ApprovalAppointRepository approvalAppointRepository;


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

    public void empsRegist(List<EmployeeRegistRequest> employeeRegistRequests, String detailErdNum, String detailErdTitle, int empCode, LocalDate detailErdRegistdate) throws MessagingException {
        for (EmployeeRegistRequest employeeRegistRequest : employeeRegistRequests) {

            /* 사원코드 생성 (yyyyMM+1) */
            String hireYearMonth = employeeRegistRequest.getHire_date().format(DateTimeFormatter.ofPattern("yyyyMM"));
            long count = employeeRepository.countByHireYearMonth(hireYearMonth);
            String newEmpCode = hireYearMonth + (count + 1);

            String erdWriter = employeeRepository.findEmpNameById(empCode);

            Department department = departmentRepository.findByDeptCode(employeeRegistRequest.getDept_code());
            Title title = titleRepository.findByTitleCode(employeeRegistRequest.getTitle_code());
            Position position = positionRepository.findByPositionCode(employeeRegistRequest.getPosition_code());

            /* Employee 객체 생성 */
            Employee newEmp = Employee.regist(
                    Integer.parseInt(newEmpCode),
                    employeeRegistRequest.getEmp_name(),
                    passwordEncoder.encode(newEmpCode),
                    employeeRegistRequest.getSocial_security_no(),
                    employeeRegistRequest.getEmail(),
                    employeeRegistRequest.getHire_date(),
                    employeeRegistRequest.getEmp_status()
            );

            newEmp.setDepartment(department);
            newEmp.setTitle(title);
            newEmp.setPosition(position);

            /* DetailByEmpRegist 객체 생성 */
            DetailByEmpRegist detailByEmpRegist = new DetailByEmpRegist(
                    detailErdNum,
                    detailErdTitle,
                    erdWriter,
                    detailErdRegistdate,
                    newEmp
            );

            /* Employee 객체에 DetailByEmpRegist 추가 */
            newEmp.addEmpRegistDetail(detailByEmpRegist);

            /* 저장 */
            employeeRepository.save(newEmp);

            /* 아이디, 비밀번호 이메일 전송 */
            emailService.sendNewEmp(newEmp);
        }
    }

    public List<DetailByEmpRegistResponseGroup> getEmpRegistList() {

        List<DetailByEmpRegist> detailByEmpRegists = detailByEmpRegistRepository.findAll();

        Map<String, List<DetailByEmpRegist>> groupedByInfo = detailByEmpRegists.stream()
                .collect(Collectors.groupingBy(empRegist ->
                        empRegist.getErd_num() + "-" +
                                empRegist.getErd_title() + "-" +
                                empRegist.getErd_writer() + "-" +
                                empRegist.getErd_registdate()));

        Map<String, List<DetailByEmpRegist>> sortedGroupedByInfo = groupedByInfo.entrySet().stream()
                .sorted(Map.Entry.<String, List<DetailByEmpRegist>>comparingByKey(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));

        List<DetailByEmpRegistResponseGroup> responseGroups = sortedGroupedByInfo.entrySet().stream()
                .map(entry -> {
                    List<DetailByEmpRegistResponse> responses = entry.getValue().stream()
                            .sorted(Comparator.comparing(DetailByEmpRegist::getErd_code))
                            .map(DetailByEmpRegistResponse::getEmpRegistList)
                            .collect(Collectors.toList());

                    DetailByEmpRegist firstElement = entry.getValue().get(0);

                    return new DetailByEmpRegistResponseGroup(
                            firstElement.getErd_num(),
                            firstElement.getErd_title(),
                            firstElement.getErd_writer(),
                            firstElement.getErd_registdate(),
                            responses
                    );
                })
                .collect(Collectors.toList());

        return responseGroups;
    }

    public EmpRegistDetailListResponse getEmpsRegistListDetail(String erdNum) {

        List<DetailByEmpRegist> detailByEmpRegists = detailByEmpRegistRepository.findEmpRegistListDetail(erdNum);

        DetailByEmpRegist detailByEmpRegist = detailByEmpRegists.get(0);

        return EmpRegistDetailListResponse.fromEntity(detailByEmpRegist.getErd_num(), detailByEmpRegist.getErd_title(), detailByEmpRegists);
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

        return getEmployeeList(employees);
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

    public List<EmpTitleListResponse> getEmpTitleList() {
        List<Title> empTitleList = titleRepository.findAll();

        return empTitleList.stream().map(EmpTitleListResponse::from).toList();
    }

    public List<GetPositionNameResponse> getPositionList() {
        List<Position> empPositionList = positionRepository.findAll();

        return empPositionList.stream().map(GetPositionNameResponse::from).toList();
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


    public List<EmployeeResponse> getOrg() {
        // 모든 사원 정보 조회
        List<Employee> employees = employeeRepository.findAll();

        // EmployeeResponse DTO로 변환
        Map<Integer, EmployeeResponse> employeeResponseMap = employees.stream()
                .collect(Collectors.toMap(
                        Employee::getEmp_code,
                        EmployeeResponse::fromEntity
                ));

        // 계층 구조 생성
        List<EmployeeResponse> rootEmployees = new ArrayList<>();
        Map<Integer, List<EmployeeResponse>> childrenMap = new HashMap<>();

        for (EmployeeResponse employee : employeeResponseMap.values()) {
            Integer parCode = employee.getPar_code();
            if (parCode == null || parCode == 0) {
                rootEmployees.add(employee);
            } else {
                childrenMap.computeIfAbsent(parCode, k -> new ArrayList<>()).add(employee);
            }
        }

        for (EmployeeResponse employee : employeeResponseMap.values()) {
            employee.setChildren(childrenMap.get(employee.getEmp_code()));
        }

        return rootEmployees;
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


public void registApp(@Valid AppRegistGroupRequest appRegistGroupRequest) {
// 1. ApprovalAppoint 객체 생성 및 저장
        ApprovalAppoint approvalAppoint = new ApprovalAppoint(
                appRegistGroupRequest.getAappCode(),
                appRegistGroupRequest.getAappNo(),
                appRegistGroupRequest.getAappDate(),
                appRegistGroupRequest.getAappTitle()
        );
        approvalAppointRepository.save(approvalAppoint);

        // 2. Employee 객체 조회
        Employee employee = employeeRepository.findByEmpCode(Integer.parseInt(appRegistGroupRequest.getEmpCode()));

        // 3. AppointDetail 객체 생성 및 저장
        AppointDetail appointDetail = AppointDetail.of(
                approvalAppoint,
                appRegistGroupRequest.getAdetBefore(),
                appRegistGroupRequest.getAdetAfter(),
                appRegistGroupRequest.getAdetType(),
                employee
        );
        appointDetailRepository.save(appointDetail);
    }

    public EmployeeListResponse getAllInfo() {

        List<Employee> allList = employeeRepository.findAll();

        return getEmployeeList(allList);
    }
}
