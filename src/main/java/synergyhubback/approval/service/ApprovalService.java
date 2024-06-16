package synergyhubback.approval.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import synergyhubback.approval.dao.LineEmpMapper;
import synergyhubback.approval.domain.entity.*;
import synergyhubback.approval.domain.repository.*;
import synergyhubback.approval.dto.request.DocRegistRequest;
import synergyhubback.approval.dto.response.FormLineResponse;
import synergyhubback.approval.dto.response.FormListResponse;
import synergyhubback.approval.dto.response.LineEmpDTO;

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
        System.out.println("afCode = " + afCode);

        String code = "";

        if (afCode == 2 || afCode == 3 || afCode == 4 || afCode == 5) {
            code = generateAttendanceCode();
            ApprovalAttendance newAttendance = null;

            if(afCode == 2){
                 newAttendance = ApprovalAttendance.of( code,
                        docRegistRequest.getApprovalAttendance().getAattSort(),
                        docRegistRequest.getApprovalAttendance().getAattStart(),
                        docRegistRequest.getApprovalAttendance().getAattEnd(),
                        docRegistRequest.getApprovalAttendance().getAattPlace(),
                        docRegistRequest.getApprovalAttendance().getAattCon()
                );
            }else if(afCode == 3){
                newAttendance = ApprovalAttendance.of( code,
                        docRegistRequest.getApprovalAttendance().getAattReason(),
                        docRegistRequest.getApprovalAttendance().getAattSort(),
                        docRegistRequest.getApprovalAttendance().getAattStart(),
                        docRegistRequest.getApprovalAttendance().getAattEnd(),
                        docRegistRequest.getApprovalAttendance().getAattPlace(),
                        docRegistRequest.getApprovalAttendance().getAattCon()
                );
            }else if(afCode == 4){
                newAttendance = ApprovalAttendance.of( code,
                        docRegistRequest.getApprovalAttendance().getAattSort(),
                        docRegistRequest.getApprovalAttendance().getAattOccur(),
                        docRegistRequest.getApprovalAttendance().getAattReason()
                );
            }else if(afCode == 5){
                newAttendance = ApprovalAttendance.of( code,
                        docRegistRequest.getApprovalAttendance().getAattSort(),
                        docRegistRequest.getApprovalAttendance().getAattStart(),
                        docRegistRequest.getApprovalAttendance().getAattEnd()
                );
            }

            approvalAttendanceRepository.save(newAttendance);
        } else if (afCode == 7 || afCode == 8 || afCode == 9) {
            code = generatePersonalCode();
            Personal newPersonal = Personal.of( code,
                    docRegistRequest.getPersonal().getApStart(),
                    docRegistRequest.getPersonal().getApEnd(),
                    docRegistRequest.getPersonal().getApContact(),
                    docRegistRequest.getPersonal().getApReason()
            );
            personalRepository.save(newPersonal);
        } else if (afCode == 1) {
            code = generateAppointCode();
            ApprovalAppoint newAppoint = ApprovalAppoint.of( code,
                    docRegistRequest.getApprovalAppoint().getAappNo(),
                    docRegistRequest.getApprovalAppoint().getAappDate(),
                    docRegistRequest.getApprovalAppoint().getAappTitle()
            );
            approvalAppointRepository.save(newAppoint);

            List<AppointDetail> AppointDetailList = docRegistRequest.getAppointDetailList();
            for(AppointDetail detail: AppointDetailList){
                AppointDetail newDetail = new AppointDetail(
                        newAppoint,
                        detail.getAdetBefore(),
                        detail.getAdetAfter(),
                        detail.getAdetType(),
                        detail.getEmpCode()
                );
                appointDetailRepository.save(newDetail);
            }
        } else {
            code = generateEtcCode();
            Etc newEtc = Etc.of(code, docRegistRequest.getEtc().getAeCon());
            etcRepository.save(newEtc);
        }

        Document newDoc = Document.of(
                docRegistRequest.getAdTitle(),
                docRegistRequest.getEmpCode(),
                docRegistRequest.getAdReportDate(),
                docRegistRequest.getAdStatus(),
                docRegistRequest.getForm(),
                code
        );

        docRepository.save(newDoc);

        int adCode = newDoc.getAdCode();
        System.out.println("adCode = " + adCode);
        if(temporary){
            // 임시저장 해야돼
        }
    }

    private String generatePersonalCode() {
        Optional<Personal> lastOptional = personalRepository.findTopByOrderByApCodeDesc();
        return generateCode(lastOptional, "AP");
    }

    private String generateEtcCode() {
        Optional<Etc> lastOptional = etcRepository.findTopByOrderByAeCodeDesc();
        return generateCode(lastOptional, "AE");
    }

    private String generateAttendanceCode(){
        Optional<ApprovalAttendance> lastOptional = approvalAttendanceRepository.findTopByOrderByAattCodeDesc();
        return generateCode(lastOptional, "AATT");
    }

    private String generateAppointCode(){
        Optional<ApprovalAppoint> lastOptional = approvalAppointRepository.findTopByOrderByAappCodeDesc();
        return generateCode(lastOptional, "AAPP");
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



//    @Transactional(readOnly = true)
//    public List<DocumentResponse> findDocList(Integer empCode) {
//        List<Document> docList = docRepository.findByEmpCodeOrderByAdReportDateDesc(empCode);
//
//        return docList.stream().map(DocumentResponse::from).toList();
//    }

}
