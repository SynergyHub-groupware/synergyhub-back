package synergyhubback.approval.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import synergyhubback.approval.dao.LineEmpMapper;
import synergyhubback.approval.domain.entity.*;
import synergyhubback.approval.domain.repository.*;
import synergyhubback.approval.dto.request.ApprovalAttachRequest;
import synergyhubback.approval.dto.request.DocRegistRequest;
import synergyhubback.approval.dto.response.*;
import synergyhubback.common.attachment.AttachmentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalService {
    private final FormRepository formRepository;
    private final LineRepository lineRepository;
    private final LineEmpMapper lineEmpMapper;
    private final DocRepository docRepository;
    private final EtcRepository etcRepository;
    private final PersonalRepository personalRepository;
    private final ApprovalAttendanceRepository approvalAttendanceRepository;
    private final ApprovalAppointRepository approvalAppointRepository;
    private final AppointDetailRepository appointDetailRepository;
    private final ApprovalBoxRepository approvalBoxRepository;
    private final ApprovalStorageRepository approvalStorageRepository;
    private final TrueLineRepository trueLineRepository;
    private final AttachmentRepository attachmentRepository;

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

    @Transactional
    public void regist(DocRegistRequest docRegistRequest, @RequestParam boolean temporary) {
        int afCode = docRegistRequest.getForm().getAfCode();
        String code = "";

        // 결재양식별 결재상세내용 해당 테이블에 저장
        if (afCode == 2 || afCode == 3 || afCode == 4 || afCode == 5) {
            code = generateAttendanceCode();
            ApprovalAttendance newAttendance = null;

            if(afCode == 2){            // 예외근무신청서
                 newAttendance = ApprovalAttendance.of( code,
                        docRegistRequest.getApprovalAttendance().getAattSort(),
                        docRegistRequest.getApprovalAttendance().getAattStart(),
                        docRegistRequest.getApprovalAttendance().getAattEnd(),
                        docRegistRequest.getApprovalAttendance().getAattPlace(),
                        docRegistRequest.getApprovalAttendance().getAattCon()
                );
            }else if(afCode == 3){      // 초과근무신청서
                newAttendance = ApprovalAttendance.of( code,
                        docRegistRequest.getApprovalAttendance().getAattReason(),
                        docRegistRequest.getApprovalAttendance().getAattSort(),
                        docRegistRequest.getApprovalAttendance().getAattStart(),
                        docRegistRequest.getApprovalAttendance().getAattEnd(),
                        docRegistRequest.getApprovalAttendance().getAattPlace(),
                        docRegistRequest.getApprovalAttendance().getAattCon()
                );
            }else if(afCode == 4){      // 지각사유서
                newAttendance = ApprovalAttendance.of( code,
                        docRegistRequest.getApprovalAttendance().getAattSort(),
                        docRegistRequest.getApprovalAttendance().getAattOccur(),
                        docRegistRequest.getApprovalAttendance().getAattReason()
                );
            }else if(afCode == 5){      // 휴가신청서
                newAttendance = ApprovalAttendance.of( code,
                        docRegistRequest.getApprovalAttendance().getAattSort(),
                        docRegistRequest.getApprovalAttendance().getAattStart(),
                        docRegistRequest.getApprovalAttendance().getAattEnd()
                );
            }

            approvalAttendanceRepository.save(newAttendance);
        } else if (afCode == 7 || afCode == 8) {        // 휴직신청서 & 사직신청서
            code = generatePersonalCode();
            Personal newPersonal = Personal.of( code,
                    docRegistRequest.getPersonal().getApStart(),
                    docRegistRequest.getPersonal().getApEnd(),
                    docRegistRequest.getPersonal().getApContact(),
                    docRegistRequest.getPersonal().getApReason()
            );
            personalRepository.save(newPersonal);
        } else if (afCode == 1) {   // 인사발령
            code = generateAppointCode();
            ApprovalAppoint newAppoint = ApprovalAppoint.of( code,
                    docRegistRequest.getApprovalAppoint().getAappNo(),
                    docRegistRequest.getApprovalAppoint().getAappDate(),
                    docRegistRequest.getApprovalAppoint().getAappTitle()
            );
            approvalAppointRepository.save(newAppoint);

            List<AppointDetail> AppointDetailList = docRegistRequest.getAppointDetailList();
            for(AppointDetail detail: AppointDetailList){
                AppointDetail newDetail = AppointDetail.of(
                        newAppoint,
                        detail.getAdetBefore(),
                        detail.getAdetAfter(),
                        detail.getAdetType(),
                        detail.getEmpCode()
                );
                appointDetailRepository.save(newDetail);
            }
        } else {    // 기타결재
            code = generateEtcCode();
            Etc newEtc = Etc.of(code, docRegistRequest.getEtc().getAeCon());
            etcRepository.save(newEtc);
        }

        // 결재문서 저장
        Document newDoc = Document.of(
                generateDocumentCode(),
                docRegistRequest.getAdTitle(),
                docRegistRequest.getEmployee(),
                docRegistRequest.getAdReportDate(),
                docRegistRequest.getAdStatus(),
                docRegistRequest.getForm(),
                code
        );
        docRepository.save(newDoc);

        // 방금 저장한 결재문서 조회해오기
        Document findDoc = docRepository.findByAdDetail(code);

        // 실결재라인 저장
        List<TrueLine> TrueLineList = docRegistRequest.getTrueLineList();
        for(TrueLine line: TrueLineList){
            TrueLine newLine = TrueLine.of(
                    findDoc,
                    line.getTalOrder(),
                    line.getTalRole(),
                    "미결재",
//                    line.getEmpCode()
                    line.getEmployee()
            );
            trueLineRepository.save(newLine);
        }

        if(temporary){  // 임시저장
            String finalCode = code;
            ApprovalBox findBox = approvalBoxRepository.findById(1).orElseThrow(() -> new IllegalArgumentException("Invalid ApprovalBox code:" + finalCode));

            ApprovalStorage newStorage = ApprovalStorage.of(findDoc, findBox);
            approvalStorageRepository.save(newStorage);
        }
    }

    private String generatePersonalCode() {
        Optional<Personal> lastOptional = personalRepository.findTopOrderByApCodeDesc();
        return generateCode(lastOptional, "AP");
    }

    private String generateEtcCode() {
        Optional<Etc> lastOptional = etcRepository.findTopOrderByAeCodeDesc();
        return generateCode(lastOptional, "AE");
    }

    private String generateAttendanceCode(){
        Optional<ApprovalAttendance> lastOptional = approvalAttendanceRepository.findTopOrderByAattCodeDesc();
        return generateCode(lastOptional, "AATT");
    }

    private String generateAppointCode(){
        Optional<ApprovalAppoint> lastOptional = approvalAppointRepository.findTopOrderByAappCodeDesc();
        return generateCode(lastOptional, "AAPP");
    }

    private String generateDocumentCode(){
        Optional<Document> lastOptional = docRepository.findTopOrderByAdCodeDesc();
        return generateCode(lastOptional, "AD");
    }

    private String generateCode(Optional<?> lastOptional, String prefix) {
        if (lastOptional.isPresent()) {
            Object lastCodeObject = lastOptional.get();
            String lastCodeStr = "";

            if (lastCodeObject instanceof Personal) {
                lastCodeStr = ((Personal) lastCodeObject).getApCode();
            } else if (lastCodeObject instanceof Etc) {
                lastCodeStr = ((Etc) lastCodeObject).getAeCode();
            } else if (lastCodeObject instanceof ApprovalAttendance) {
                lastCodeStr = ((ApprovalAttendance) lastCodeObject).getAattCode();
            } else if (lastCodeObject instanceof ApprovalAppoint) {
                lastCodeStr = ((ApprovalAppoint) lastCodeObject).getAappCode();
            } else if (lastCodeObject instanceof Document) {
                lastCodeStr = ((Document) lastCodeObject).getAdCode();
            } else if (lastCodeObject instanceof String) {
                lastCodeStr = (String) lastCodeObject;
            } else {
                throw new IllegalArgumentException("Unsupported entity type");
            }

            String numericPart = lastCodeStr.replaceAll("[^\\d]", "");
            String nonNumericPart = lastCodeStr.replaceAll("[\\d]", "");

            int numericValue = Integer.parseInt(numericPart) + 1;

            return nonNumericPart + numericValue;
        } else {
            return prefix + "1";
        }
    }

    public void saveAttachment(ApprovalAttachRequest approvalAttachRequest) {
    }

    @Transactional(readOnly = true)
    public List<DocListResponse> findDocList(Integer empCode, String status) {
        switch (status){
            case "waiting" : status = "대기"; break;
            case "process" : status = "진행중"; break;
            case "return" : status  = "반려"; break;
            case "complete" : status = "완료"; break;
        }
        List<TrueLine> docList = trueLineRepository.findTrueLineWithPendingStatus(empCode, status);

        return docList.stream().map(DocListResponse::from).toList();
    }
}
