package synergyhubback.approval.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
