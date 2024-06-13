package synergyhubback.approval.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synergyhubback.approval.dto.response.DocumentResponse;
import synergyhubback.approval.dto.response.FormLineResponse;
import synergyhubback.approval.dto.response.FormListResponse;
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

//    @GetMapping("/formLine")
//    public ResponseEntity<List<FormLineResponse>> findFormLine(@RequestParam(required = false) final Integer lsCode) {
//        List<FormLineResponse> formLine;
//
//        if (lsCode != null && lsCode > 0) {
//            formLine = approvalService.findFormLine(lsCode);
//        } else {
//            formLine = approvalService.findAllFormLines();
//        }
//
//        return ResponseEntity.ok(formLine);
//    }

    @GetMapping("/document")
    public ResponseEntity<List<DocumentResponse>> findDocList(@RequestParam(required = false) final Integer empCode){
        final List<DocumentResponse> docList = approvalService.findDocList(empCode);
        return ResponseEntity.ok(docList);
    }

}
