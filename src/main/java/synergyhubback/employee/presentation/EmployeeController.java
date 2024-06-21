package synergyhubback.employee.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import synergyhubback.auth.util.TokenUtils;
import synergyhubback.employee.dto.request.EmployeeRegistRequest;
import synergyhubback.employee.dto.request.ResetEmpPassRequest;
import synergyhubback.employee.dto.response.*;
import synergyhubback.employee.service.EmployeeService;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;


    /* 로그아웃 시 토큰 무효화 */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserDetails userDetails) {

        employeeService.updateRefreshToken(Integer.parseInt(userDetails.getUsername()), null);

        return ResponseEntity.ok().build();
    }

    /* 사원 등록 */
    @PostMapping("/empRegist")
    public ResponseEntity<Void> empRegist (@RequestBody @Valid EmployeeRegistRequest employeeRegistRequest) {

        employeeService.empRegist(employeeRegistRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /* 내 정보 조회 */
    @GetMapping("/myInfo")
    public ResponseEntity<MyInfoResponse> getMyInfo(@RequestHeader("Authorization") String token) {

        String jwtToken = TokenUtils.getToken(token);
        String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
        int empCode = Integer.parseInt(tokenEmpCode);

        MyInfoResponse myInfoResponse = employeeService.getMyInfo(empCode);

        return ResponseEntity.ok(myInfoResponse);
    }

    /* 인사기록카드 조회 하고 싶어요 */
    @GetMapping("/recordCard")
    public ResponseEntity<RecordCardResponse> getRecordCard(@RequestHeader("Authorization") String token) {

        String jwtToken = TokenUtils.getToken(token);
        String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
        int empCode = Integer.parseInt(tokenEmpCode);

        RecordCardResponse recordCardResponse = employeeService.getRecordCard(empCode);

        return ResponseEntity.ok(recordCardResponse);

    }

    /* 팀원 정보 조회 */
    @GetMapping("/employeeList")
    public ResponseEntity<EmployeeListResponse> employeeList(@RequestHeader("Authorization") String token) {

        String jwtToken = TokenUtils.getToken(token);
        String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
        int empCode = Integer.parseInt(tokenEmpCode);

        System.out.println("token : " + token);
        System.out.println("empCode : " + empCode);

        EmployeeListResponse employeeListResponse = employeeService.employeeList(empCode);

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

    /* 비밀번호 초기화 */
    @PatchMapping("/resetEmpPass/{emp_code}")
    public ResponseEntity<ResetEmpPassRequest> patchEmpPass(@PathVariable int emp_code, @RequestHeader("Authorization") String token) {

        String jwtToken = TokenUtils.getToken(token);

        String tokenDeptCode = TokenUtils.getDeptCode(jwtToken);

        List<String> allowedDeptCodes = List.of("D4", "D5", "D6");      // 인사부서(인사부, 채용팀, 교육개발팀) 인원만 비밀번호 초기화 가능

//        System.out.println("allowedDeptCodes : " + allowedDeptCodes);
//        System.out.println("tokenDeptCode : " + tokenDeptCode);

//        if(tokenDeptCode == null || !allowedDeptCodes.contains(tokenDeptCode)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }

        employeeService.resetEmpPass(emp_code);

        return ResponseEntity.ok().build();
    }

    /* 내 정보 조회 */
//    @GetMapping("/myInfo")
//    public ResponseEntity<MyInfoResponse> getMyInfo(@RequestHeader("Authorization") String token) {
//
//        String jwtToken = TokenUtils.getToken(token);
//        String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
//        int empCode = Integer.parseInt(tokenEmpCode);
//
//        MyInfoResponse myInfoResponse = employeeService.getMyInfo(empCode);
//
//        return ResponseEntity.ok(myInfoResponse);
//    }
//
//    /* 팀원 정보 조회 */
//    @GetMapping("/employeeList")
//    public ResponseEntity<EmployeeListResponse> employeeList(@RequestHeader("Authorization") String token) {
//
//        String jwtToken = TokenUtils.getToken(token);
//        String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
//        int empCode = Integer.parseInt(tokenEmpCode);
//
//        System.out.println("token : " + token);
//        System.out.println("empCode : " + empCode);
//
//        EmployeeListResponse employeeListResponse = employeeService.employeeList(empCode);
//
//        return ResponseEntity.ok(employeeListResponse);
//    }
}

