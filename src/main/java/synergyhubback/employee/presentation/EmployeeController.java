package synergyhubback.employee.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synergyhubback.employee.dto.request.EmployeeRegistRequest;
import synergyhubback.employee.dto.response.EmployeeListResponse;
import synergyhubback.employee.dto.response.MyInfoResponse;
import synergyhubback.employee.dto.response.RecordCardResponse;
import synergyhubback.employee.service.EmployeeService;

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
}
