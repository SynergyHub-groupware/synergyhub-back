package synergyhubback.approval.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.approval.domain.entity.Form;
import synergyhubback.approval.domain.repository.FormRepository;
import synergyhubback.approval.dto.response.FormListResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalService {
    private final FormRepository formRepository;

    @Transactional(readOnly = true)
    public List<FormListResponse> findFormList() {
        List<Form> formList = formRepository.findAll();
        return formList.stream().map(FormListResponse::from).toList();
    }
}
