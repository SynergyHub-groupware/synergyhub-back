package synergyhubback.employee.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synergyhubback.employee.dto.request.EmployeeRegistRequest;
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
}