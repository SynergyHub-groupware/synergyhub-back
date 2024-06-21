package synergyhubback.post.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import synergyhubback.common.attachment.AttachmentEntity;
import synergyhubback.post.domain.entity.*;
import synergyhubback.post.domain.type.PostCommSet;
import synergyhubback.post.dto.request.PostRequest;
import synergyhubback.post.service.PostService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    @Value("${image.post-image-dir}")
    private String POST_FILE_DIR;

    private final PostService postService;

    @PostMapping("/commentAdd")
    public ResponseEntity<CommentEntity> commentAdd(@RequestParam CommentEntity commentEntity) {
        return ResponseEntity.ok(postService.commentAdd(commentEntity));
    }

    @GetMapping("/commentList/{postCode}")
    public ResponseEntity<List<CommentEntity>> getCommentList(@PathVariable String postCode) {
        return ResponseEntity.ok(postService.getCommentList(postCode));
    }

    @GetMapping("/downloadFile/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(POST_FILE_DIR).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }


    @GetMapping("/getDetail/{postCode}")
    public ResponseEntity<PostEntity> callGETDetail(@PathVariable("postCode") String postCode) {
        System.out.println("callGETDetail stared");
        PostEntity post = postService.getDetail(postCode);
        System.out.println(post);
        post.setPostCommSet(PostCommSet.fromValue(post.getPostCommSet().getValue())); // 이 부분 확인
        return ResponseEntity.ok(post);
    }
    @GetMapping("/callGETFile/{postCode}")
    public ResponseEntity<List<AttachmentEntity>> callGETFile(@PathVariable("postCode") String postCode) {
        System.out.println("callGETFile stared");
        List<AttachmentEntity> fileList = postService.getFile(postCode);
        System.out.println("callGETFile"+fileList);
        return ResponseEntity.ok(fileList);
    }

    @GetMapping("/callGETInboardList/{lowBoardCode}")
    public ResponseEntity<List<PostEntity>> callGETInboardList(Pageable pageable,@PathVariable("lowBoardCode") Integer lowBoardCode) {
        System.out.println("callGETInboardList stared");
        List<PostEntity> posts = postService.InboardList( pageable,lowBoardCode);
        System.out.println(posts);
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/callGETInboardPinList/{lowBoardCode}")
    public ResponseEntity<List<PostEntity>> callGETInboardPinList(@PageableDefault Pageable pageable,@PathVariable("lowBoardCode") Integer boardCode) {
        System.out.println("callGETInboardPinList stared");
        List<PostEntity> posts = postService.InboardPinList(pageable,boardCode);
        System.out.println(posts);
        return ResponseEntity.ok(posts);
    }


    @GetMapping("/list")
    public ResponseEntity<List<PostEntity>> findPostList(@PageableDefault Pageable pageable) {
        System.out.println("getList stared");
        List<PostEntity> posts = postService.getAllPostList(pageable);
        System.out.println(posts);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/sortList")
    public ResponseEntity<List<PostSortEntity>> findPostSortList() {
        List<PostSortEntity> postSortEntities = postService.getAllPostSortList();
        return ResponseEntity.ok(postSortEntities);
    }

    @GetMapping("/getAllBoard")
    public ResponseEntity<List<BoardEntity>> getAllBoard() {
        System.out.println("getAllBoard stared");
        List<BoardEntity> boardList = postService.getAllBoard();
        System.out.println("getAllBoard" + boardList);
        return ResponseEntity.ok(boardList);
    }

    @GetMapping("/getLowBoard/{boardCode}")
    public ResponseEntity<List<LowBoardEntity>> getLowBoard(@PathVariable("boardCode") Integer boardCode) {
        List<LowBoardEntity> lowBoardList = postService.getLowBoard(boardCode);
        return ResponseEntity.ok(lowBoardList);
    }
    @GetMapping("/getAllLowBoard")
    public ResponseEntity<List<LowBoardEntity>> getLowBoardPin() {
        List<LowBoardEntity> lowBoardList = postService.getAllLowBoard();
        System.out.println(lowBoardList);
        return ResponseEntity.ok(lowBoardList);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestParam(value = "attachFile", required = false) List<MultipartFile> attachFile,
                                        @RequestParam("postName") String postName,
                                        @RequestParam("postCon") String postCon,
                                        @RequestParam("lowBoardCode") int lowBoardCode,
                                        @RequestParam(value = "postCommSet", defaultValue = "4") int postCommSet,
                                        @RequestParam(value = "fixStatus", defaultValue = "N") char fixStatus,
                                        @RequestParam(value = "noticeStatus", defaultValue = "N") char noticeStatus,
                                        @RequestParam("psCode") int psCode,
                                        Model model) {
        // 상품 정보 저장
        System.out.println("게시글 등록 메소드 작동시작");
        System.out.println(lowBoardCode);
        System.out.println(attachFile);
        PostCommSet commSet = PostCommSet.fromValue(postCommSet);
        System.out.println(commSet);

        /* 상품 등록하기 */
        PostRequest newPost = new PostRequest();
        String numericPart = postService.LastPost().getPostCode().substring(1); // "020"
        int lastNumber = Integer.parseInt(numericPart); // 20
        int nextNumber = lastNumber + 1;

        newPost.setPostCode("P0" + nextNumber);
        newPost.setPostName(postName);
        newPost.setPostCon(postCon);
        newPost.setPostCommSet(commSet);
        newPost.setFixStatus(fixStatus);
        newPost.setNoticeStatus(noticeStatus);
        newPost.setLowBoardCode(lowBoardCode);
        newPost.setPsCode(psCode);
        System.out.println(lowBoardCode);

        PostEntity post = postService.insertPost(newPost);


        /* 경로 설정 */
        String fileUploadDir = POST_FILE_DIR;

        File dir1 = new File(fileUploadDir);

        /* 디렉토리가 없을 경우 생성한다. */
        if (!dir1.exists()) {
            dir1.mkdirs();
        }
        if (attachFile != null) {
            // 처리 로직

            List<AttachmentEntity> FileList = new ArrayList<>();

            try {
                for (int i = 0; i < attachFile.size(); i++) {
                    /* 첨부파일이 실제로 존재하는 경우 로직 수행 */
                    if (attachFile.get(i).getSize() > 0) {

                        String originalFileName = attachFile.get(i).getOriginalFilename();

                        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
                        String saveFileName = UUID.randomUUID() + ext;

                        /* 서버의 설정 디렉토리에 파일 저장하기 */
                        attachFile.get(i).transferTo(new File(fileUploadDir + "/" + saveFileName));

                        /* 가장 최신 게시글 코드 조회 */
                        PostEntity lastPost = postService.LastPost();

                        /* DB에 저장할 파일의 정보 처리 */
                        AttachmentEntity fileInfo = new AttachmentEntity(originalFileName, saveFileName,fileUploadDir + "/" +saveFileName,lastPost.getPostCode());

                        /* 리스트에 파일 정보 저장 */
                        FileList.add(fileInfo);
                    }
                    /* 가장 최신 게시글 코드 조회 */
                    PostEntity lastPost = postService.LastPost();
                    
                    
                }
                /* 파일 리스트를 한 번에 DB에 저장 */
                postService.registFileList(FileList);

                model.addAttribute("message", "파일 업로드에 성공하였습니다.");

            } catch (IOException e) {
                /* 파일 저장 중간에 실패 시, 이전에 저장된 파일 삭제 */

                model.addAttribute("message", "파일 업로드에 실패하였습니다.");
            }

        } else {

            /* 업로드 파일에 대한 정보를 담을 리스트 */

        }
        return ResponseEntity.ok(post);


    }
}

