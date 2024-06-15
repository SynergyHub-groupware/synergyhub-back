package synergyhubback.approval.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.approval.dao.LineEmpMapper;
import synergyhubback.approval.domain.entity.Form;
import synergyhubback.approval.domain.entity.Line;
import synergyhubback.approval.domain.repository.DocRepository;
import synergyhubback.approval.domain.repository.FormRepository;
import synergyhubback.approval.domain.repository.LineRepository;
import synergyhubback.approval.dto.response.FormLineResponse;
import synergyhubback.approval.dto.response.FormListResponse;
import synergyhubback.approval.dto.response.LineEmpDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalService {
    private final FormRepository formRepository;
    private final LineRepository lineRepository;
    private final DocRepository docRepository;
    private final LineEmpMapper lineEmpMapper;

    @Transactional(readOnly = true)
    public List<FormListResponse> findFormList() {
        List<Form> formList = formRepository.findAll();
        return formList.stream().map(FormListResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<FormLineResponse> findFormLine(final Integer lsCode) {
        List<Line> formLine = null;

        if (lsCode != null && lsCode > 0) {
            formLine = lineRepository.findByLineSortLsCodeOrderByAlOrderAsc(lsCode);
        } else {
            formLine = lineRepository.findAll();
        }

        return formLine.stream().map(FormLineResponse::from).toList();
    }

    public List<LineEmpDTO> findLineEmpList(final String deptCode, final String titleCode, final Integer lsCode) {
        return lineEmpMapper.findLineEmpList(deptCode, titleCode, lsCode);
    }


//    @Transactional(readOnly = true)
//    public List<DocumentResponse> findDocList(Integer empCode) {
//        List<Document> docList = docRepository.findByEmpCodeOrderByAdReportDateDesc(empCode);
//
//        return docList.stream().map(DocumentResponse::from).toList();
//    }

}
