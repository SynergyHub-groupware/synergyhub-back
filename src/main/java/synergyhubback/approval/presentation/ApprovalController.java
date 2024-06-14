package synergyhubback.approval.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synergyhubback.approval.dto.response.FormLineResponse;
import synergyhubback.approval.dto.response.FormListResponse;
import synergyhubback.approval.dto.response.LineEmpDTO;
import synergyhubback.approval.service.ApprovalService;

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
    public ResponseEntity<List<LineEmpDTO>> findLineEmpList(@RequestParam final String deptCode, @RequestParam final String titleCode){
        final List<LineEmpDTO> lineEmpList = approvalService.findLineEmpList(deptCode, titleCode);
        return ResponseEntity.ok(lineEmpList);
    }

    

//    @GetMapping("/document")
//    public ResponseEntity<List<DocumentResponse>> findDocList(@RequestParam(required = false) final Integer empCode){
//        final List<DocumentResponse> docList = approvalService.findDocList(empCode);
//        return ResponseEntity.ok(docList);
//    }


}
