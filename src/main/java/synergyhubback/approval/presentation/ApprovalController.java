package synergyhubback.approval.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synergyhubback.approval.dto.request.ApprovalAttachRequest;
import synergyhubback.approval.dto.request.DocRegistRequest;
import synergyhubback.approval.dto.response.*;
import synergyhubback.approval.service.ApprovalService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/approval")
@RequiredArgsConstructor
public class ApprovalController {
    private final ApprovalService approvalService;

    @GetMapping("/formList")
    public ResponseEntity<List<FormListResponse>> findFormList() {
        List<FormListResponse> formList = approvalService.findFormList();
        return ResponseEntity.ok(formList);
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
    public ResponseEntity<Void> regist(@RequestBody @Valid final DocRegistRequest docRegistRequest, @RequestParam boolean temporary){
        approvalService.regist(docRegistRequest, temporary);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/saveAttachment")
    public ResponseEntity<Void> saveAttachment(@RequestBody @Valid final ApprovalAttachRequest approvalAttachRequest){
        approvalService.saveAttachment(approvalAttachRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/send/document")
    public ResponseEntity<List<DocListResponse>> findDocList(@RequestParam final Integer empCode, @RequestParam final String status){
        final List<DocListResponse> docList = approvalService.findDocList(empCode, status);
        return ResponseEntity.ok(docList);
    }


}
