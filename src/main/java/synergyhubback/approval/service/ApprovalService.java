package synergyhubback.approval.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import synergyhubback.approval.dao.LineEmpMapper;
import synergyhubback.approval.domain.entity.*;
import synergyhubback.approval.domain.repository.*;
import synergyhubback.approval.dto.request.DocRegistRequest;
import synergyhubback.approval.dto.response.*;
import synergyhubback.common.attachment.AttachmentEntity;
import synergyhubback.common.attachment.AttachmentRepository;
import synergyhubback.employee.domain.entity.Employee;
import synergyhubback.employee.domain.repository.EmployeeRepository;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalService {

    private final EmployeeRepository employeeRepository;
    @Value("${file.approval-dir}")
    private String approvalDir;

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
    public FormContentResponse findFormContent(final int afCode) {
        Form formContent = formRepository.findById(afCode).orElse(null);

        if (formContent == null) {
            throw new RuntimeException("Form not found for afCode: " + afCode);
        }

        return FormContentResponse.from(formContent);
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
    public void regist(DocRegistRequest docRegistRequest, MultipartFile[] files, @RequestParam boolean temporary) {
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
                Employee employee = employeeRepository.findByEmpCode(detail.getEmployee().getEmp_code());

                AppointDetail newDetail = AppointDetail.of(
                        newAppoint,
                        detail.getAdetBefore(),
                        detail.getAdetAfter(),
                        detail.getAdetType(),
                        employee
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
                    line.getEmployee()
            );
            trueLineRepository.save(newLine);
        }

        // 첨부파일 저장
        File uploadDirectory = new File(approvalDir);   // 업로드 디렉토리가 존재하지 않으면 생성합니다.
        if (!uploadDirectory.exists()) uploadDirectory.mkdirs();

        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            String saveFileName = generateSaveFileName(originalFileName);

            // 파일 저장 경로 생성
            File destFile = new File(approvalDir + File.separator + saveFileName);

            // 파일을 로컬에 저장
            try {
                file.transferTo(destFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 첨부파일 테이블에 정보 저장
            AttachmentEntity newAttachment = AttachmentEntity.of(
                    originalFileName,
                    saveFileName,
                    approvalDir,
                    findDoc.getAdCode()
            );
            attachmentRepository.save(newAttachment);
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

    private String generateSaveFileName(String originalFileName) {
        // UUID를 사용하여 고유한 파일명 생성
        String uuid = UUID.randomUUID().toString();
        String extension = "";

        // 확장자가 있으면 분리했다가 다시 합쳐서 반환
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex > 0) extension = originalFileName.substring(dotIndex);

        return uuid + extension;
    }

    private Pageable getPageable(final Integer page){
        return PageRequest.of(page - 1, 10);
    }

    @Transactional(readOnly = true)
    public List<DocListResponse> findDocList(final Integer empCode, String status) {
        switch (status){
            case "waiting" : status = "대기"; break;
            case "process" : status = "진행중"; break;
            case "return" : status  = "반려"; break;
            case "complete" : status = "완료"; break;
        }
        List<TrueLine> docList = trueLineRepository.findTrueLineWithPendingStatus(empCode, status);

        return docList.stream().map(DocListResponse::from).toList();
    }

//    public Page<DocListResponse> findDocList(final Integer page, final Integer empCode, String status) {
//        switch (status){
//            case "waiting" : status = "대기"; break;
//            case "process" : status = "진행중"; break;
//            case "return" : status  = "반려"; break;
//            case "complete" : status = "완료"; break;
//        }
//        Page<TrueLine> docList = trueLineRepository.findTrueLineWithPendingStatus(getPageable(page), empCode, status);
//        System.out.println("docList = " + docList);
//
//        return docList.map(DocListResponse::from);
//    }

    public List<ViewLineResponse> findViwLineList(final String adCode) {
        List<TrueLine> viewLineList = trueLineRepository.findViewLineList(adCode);
        return viewLineList.stream().map(ViewLineResponse::from).toList();
    }

    public ApResponse findApDetail(final String adDetail) {
        Personal viewDetail = personalRepository.findById(adDetail).orElseThrow(() -> new IllegalArgumentException("Invalid adDetail:" + adDetail));
        return ApResponse.from(viewDetail);
    }

    public AeResponse findAeDetail(final String adDetail) {
        Etc viewDetail = etcRepository.findById(adDetail).orElseThrow(() -> new IllegalArgumentException("Invalid adDetail:" + adDetail));
        return AeResponse.from(viewDetail);
    }

    public AattResponse findAattDetail(final String adDetail) {
        ApprovalAttendance viewDetail = approvalAttendanceRepository.findById(adDetail).orElseThrow(() -> new IllegalArgumentException("Invalid adDetail:" + adDetail));
        return AattResponse.from(viewDetail);
    }

    public List<AappResponse> findAappDetail(final String adDetail) {
        // 대한님 출력내용 확인 후, reponse 구성 수정필요
        List<AppointDetail> viewDetail = appointDetailRepository.findByAdDetail(adDetail);
        return viewDetail.stream().map(AappResponse::from).toList();
    }

    public List<AttachmentResponse> findAttachList(final String adCode) {
        List<AttachmentEntity> attachList = attachmentRepository.findByAttachSort(adCode);
        return attachList.stream().map(AttachmentResponse::from).toList();
    }

    public Resource downloadAttach(final String attachSave) {
        try {
            Path filePath = Paths.get(approvalDir).resolve(attachSave).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + attachSave);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found " + attachSave, e);
        }
    }

    public void deleteDocument(final String adCode) {
        // 첨부파일 삭제
        List<AttachmentEntity> attachList = attachmentRepository.findByAttachSort(adCode);
        if(attachList != null && !attachList.isEmpty()){
            attachmentRepository.deleteAll(attachList);         // db에서 삭제

            for (AttachmentEntity attachment : attachList) {    // 로컬 폴더에서 파일삭제
                String attachSave = attachment.getAttachSave();
                try {
                    Path filePath = Paths.get(attachment.getAttachUrl(), attachSave);
                    Files.delete(filePath);
                } catch (Exception e) {
                    System.err.println("Failed to delete file: " + attachSave);
                    e.printStackTrace();
                }
            }
        }

        // 상세내용 삭제
        String adDetail = docRepository.findAdDetailById(adCode);

        Pattern pattern = Pattern.compile("([a-zA-Z]+)(\\d+)");
        Matcher matcher = pattern.matcher(adDetail);

        if (matcher.matches()) {
            String textPart = matcher.group(1);
            switch (textPart){
                case "AP": personalRepository.deleteById(adDetail); break;
                case "AE": etcRepository.deleteById(adDetail); break;
                case "AATT": approvalAttendanceRepository.deleteById(adDetail); break;
                case "AAPP": approvalAppointRepository.deleteById(adDetail); break;
            }
        }

        // 결재보관내역 삭제
        List<ApprovalStorage> storageList = approvalStorageRepository.findByDocument_AdCode(adCode);
        if(storageList != null && !storageList.isEmpty()) approvalStorageRepository.deleteByDocument_AdCode(adCode);

        // 실결재라인 삭제
        trueLineRepository.deleteByDocument_AdCode(adCode);

        // 결재문서 삭제
        docRepository.deleteById(adCode);
    }

    @Transactional
    public void modifyStatus(final String adCode) {
        Document foundDocument = docRepository.findById(adCode).orElseThrow(() -> new RuntimeException("Document not found with adCode: " + adCode));
        foundDocument.modifyAdStatus("수정중");
        docRepository.save(foundDocument);
    }
}
