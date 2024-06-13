package synergyhubback.approval.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.approval.domain.entity.Document;
import synergyhubback.approval.domain.entity.Form;
import synergyhubback.approval.domain.entity.Line;
import synergyhubback.approval.domain.repository.DocRepository;
import synergyhubback.approval.domain.repository.FormRepository;
import synergyhubback.approval.domain.repository.LineRepository;
import synergyhubback.approval.dto.response.DocumentResponse;
import synergyhubback.approval.dto.response.FormLineResponse;
import synergyhubback.approval.dto.response.FormListResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalService {
    private final FormRepository formRepository;
    private final LineRepository lineRepository;
    private final DocRepository docRepository;

    @Transactional(readOnly = true)
    public List<FormListResponse> findFormList() {
        List<Form> formList = formRepository.findAll();
        return formList.stream().map(FormListResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<FormLineResponse> findFormLine(final int lsCode) {
        List<Line> formLine = null;

        if(lsCode > 0) formLine = lineRepository.findByLineSortLsCodeOrderByAlOrderAsc(lsCode);
        else formLine = lineRepository.findAll();

        return formLine.stream().map(FormLineResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<DocumentResponse> findDocList(final Integer empCode) {
        List<Document> docList = docRepository.findByEmpCode(empCode);

        return docList.stream().map(DocumentResponse::from).toList();
    }

//    @Transactional(readOnly = true)
//    public List<FormLineResponse> findFormLine(final int lsCode) {
//        List<Line> formLine;
//
//        formLine = lineRepository.findByLineSortLsCodeOrderByAlOrderAsc(lsCode);
//
//        return formLine.stream().map(FormLineResponse::from).toList();
//    }
//
//    @Transactional(readOnly = true)
//    public List<FormLineResponse> findAllFormLines() {
//        List<Line> formLines = lineRepository.findAll();
//        return formLines.stream().map(FormLineResponse::from).toList();
//    }

}
