package synergyhubback.approval.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import synergyhubback.approval.dto.request.DocRegistRequest;
import synergyhubback.approval.dto.response.*;
import synergyhubback.approval.service.ApprovalService;
import synergyhubback.auth.util.TokenUtils;
import synergyhubback.employee.dto.response.EmployeeListResponse;
import synergyhubback.employee.service.EmployeeService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Clob;
import java.util.List;

@RestController
@RequestMapping("/approval")
@RequiredArgsConstructor
public class ApprovalController {

    @Value("${file.approval-dir}")
    private String approvalDir;

    private final ApprovalService approvalService;
    private final EmployeeService employeeService;

    @GetMapping("/formList")
    public ResponseEntity<List<FormListResponse>> findFormList(){
        List<FormListResponse> formList = approvalService.findFormList();
        return ResponseEntity.ok(formList);
    }

    @GetMapping("/formContent")
    public ResponseEntity<FormContentResponse> findFormContent(@RequestParam final int afCode){
        FormContentResponse formContent = approvalService.findFormContent(afCode);
        return ResponseEntity.ok(formContent);
    }

    @GetMapping("/formLine")
    public ResponseEntity<List<FormLineResponse>> findFormLine(@RequestParam(required = false) final Integer lsCode){
        final List<FormLineResponse> formLine = approvalService.findFormLine(lsCode);
        return ResponseEntity.ok(formLine);
    }

    @GetMapping("/formLineEmp")
    public ResponseEntity<List<LineEmpDTO>> findLineEmpList(@RequestParam final String deptCode, @RequestParam final String titleCode, @RequestParam final Integer lsCode){
        final List<LineEmpDTO> lineEmpList = approvalService.findLineEmpList(deptCode, titleCode, lsCode);
        return ResponseEntity.ok(lineEmpList);
    }

    @GetMapping("/sign")
    public ResponseEntity<Resource> getSign(@RequestParam Integer empCode) {
        String signPath = "C:/SynergyHub/Signimgs/" + empCode + ".png";
        Path filePath = Paths.get(signPath);
        Resource resource = new FileSystemResource(filePath);

        if (!resource.exists()) return ResponseEntity.notFound().build();

        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @PostMapping("/regist")
    public ResponseEntity<Void> regist(@RequestParam("document") String documentJson, @RequestParam(value = "files", required = false) MultipartFile[] files, @RequestParam boolean temporary){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            DocRegistRequest docRegistRequest = objectMapper.registerModule(new JavaTimeModule()).readValue(documentJson, DocRegistRequest.class);

            if (files == null) files = new MultipartFile[0];        // files가 null이면 빈 배열로 초기화하여 전달

            approvalService.regist(docRegistRequest, files, temporary);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/send/document")
    public ResponseEntity<List<DocListResponse>> findDocList(@RequestParam final Integer empCode, @RequestParam final String status){
        final List<DocListResponse> docList = approvalService.findDocList(empCode, status);
        return ResponseEntity.ok(docList);
    }

    @GetMapping("/usingToken")
    public ResponseEntity<EmployeeListResponse>  usingToken(@RequestHeader("Authorization") String token) {
        String jwtToken = TokenUtils.getToken(token);
        String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
        int empCode = Integer.parseInt(tokenEmpCode);
        EmployeeListResponse employeeListResponse = employeeService.getEmployeeList(empCode);

        return ResponseEntity.ok(employeeListResponse);
    }

}
