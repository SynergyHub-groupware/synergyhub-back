package synergyhubback.employee.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import synergyhubback.employee.dto.request.EmployeeRegistRequest;
import synergyhubback.employee.dto.response.*;
import synergyhubback.employee.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    /* 사원 등록 */
    @PostMapping("/empRegist")
    public ResponseEntity<Void> empRegist (@RequestBody @Valid EmployeeRegistRequest employeeRegistRequest) {

        employeeService.empRegist(employeeRegistRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /* 내 정보 조회 */
    @GetMapping("/{emp_code}")
    public ResponseEntity<MyInfoResponse> getMyInfo(@PathVariable int emp_code) {

        MyInfoResponse myInfoResponse = employeeService.getMyInfo(emp_code);

        return ResponseEntity.ok(myInfoResponse);
    }

    /* 인사기록카드 조회 하고 싶어요 */
    @GetMapping("/recordCard/{emp_code}")
    public ResponseEntity<RecordCardResponse> getRecordCard(@PathVariable int emp_code) {

        RecordCardResponse recordCardResponse = employeeService.getRecordCard(emp_code);

        return ResponseEntity.ok(recordCardResponse);

    }

    /* 팀원 정보 조회 */
    @GetMapping("/employeeList/{emp_code}")
    public ResponseEntity<EmployeeListResponse> getEmployeeList(@PathVariable int emp_code) {

        EmployeeListResponse employeeListResponse = employeeService.getEmployeeList(emp_code);

        return ResponseEntity.ok(employeeListResponse);
    }

    /* 부서 전체 조회 */
    @GetMapping("/departments")
    public ResponseEntity<List<GetDeptTitleResponse>> getDepartments() {

        List<GetDeptTitleResponse> getDeptTitleResponse = employeeService.getDepartments();

        return ResponseEntity.ok(getDeptTitleResponse);
    }

    /* 부서 상세 조회 */
    @GetMapping("/departmentList/{dept_code}")
    public ResponseEntity<DepartmentResponse> getDepartmentList(@PathVariable String dept_code) {

        DepartmentResponse departmentResponse = employeeService.getDepartmentList(dept_code);

        return ResponseEntity.ok(departmentResponse);
    }

    /* orgChart 조회 */
    @GetMapping("/org")
    public ResponseEntity<List<OrgResponse>> getOrg() {

        List<OrgResponse> orgResponses = employeeService.getOrgEmps();

        return ResponseEntity.ok(orgResponses);
    }

    /* 조직도 상세 조회 */
    @GetMapping("/orgDetail/{emp_code}")
    public ResponseEntity<OrgDetailResponse> getOrgDetail(@PathVariable int emp_code) {

        OrgDetailResponse orgDetailResponse = employeeService.getOrgEmpDetail(emp_code);

        return ResponseEntity.ok(orgDetailResponse);
    }

    /* 로그아웃 시 토큰 무효화 */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserDetails userDetails) {

        employeeService.updateRefreshToken(Integer.parseInt(userDetails.getUsername()), null);

        return ResponseEntity.ok().build();
    }
}

