package synergyhubback.approval.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import synergyhubback.approval.dto.request.DocRegistRequest;
import synergyhubback.approval.dto.response.*;
import synergyhubback.approval.service.ApprovalService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/approval")
@RequiredArgsConstructor
public class ApprovalController {

    @Value("${file.approval-dir}")
    private String approvalDir;

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

//    @PostMapping("/regist")
//    public ResponseEntity<Void> regist(@RequestBody @Valid final DocRegistRequest docRegistRequest, @RequestParam boolean temporary){
//        approvalService.regist(docRegistRequest, temporary);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @PostMapping("/regist")
    public ResponseEntity<Void> regist(@RequestParam("document") String documentJson, @RequestParam(value = "files", required = false) MultipartFile[] files, @RequestParam boolean temporary){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            DocRegistRequest docRegistRequest = objectMapper.registerModule(new JavaTimeModule()).readValue(documentJson, DocRegistRequest.class);

            // files가 null이면 빈 배열로 초기화하여 전달
            if (files == null) files = new MultipartFile[0];

            approvalService.regist(docRegistRequest, files, temporary);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


//    @PostMapping("/attachment/regist")
//    public ResponseEntity<Void> registAttachment(@RequestParam("files") MultipartFile[] files) {
//        try {
//            File uploadDirectory = new File(approvalDir);   // 업로드 디렉토리가 존재하지 않으면 생성합니다.
//            if (!uploadDirectory.exists()) uploadDirectory.mkdirs();
//
//            for (MultipartFile file : files) {
//                // 파일 저장 경로 생성
//                File destFile = new File(approvalDir + File.separator + file.getOriginalFilename());
//
//                // 파일을 로컬에 저장
//                file.transferTo(destFile);
//                System.out.println("Received file: " + file.getOriginalFilename());
//            }
//
//            approvalService.registAttachment(files);
//
//            return ResponseEntity.ok().build();     // 파일 저장 완료 후, 클라이언트에 성공 응답을 보냅니다.
//        } catch (IOException e) {                   // 파일 처리 중 오류가 발생한 경우
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @GetMapping("/send/document")
    public ResponseEntity<List<DocListResponse>> findDocList(@RequestParam final Integer empCode, @RequestParam final String status){
        final List<DocListResponse> docList = approvalService.findDocList(empCode, status);
        return ResponseEntity.ok(docList);
    }


}
