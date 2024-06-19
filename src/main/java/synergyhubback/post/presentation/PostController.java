package synergyhubback.post.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import synergyhubback.common.attachment.AttachmentEntity;
import synergyhubback.post.domain.entity.BoardEntity;
import synergyhubback.post.domain.entity.LowBoardEntity;
import synergyhubback.post.domain.entity.PostEntity;
import synergyhubback.post.domain.type.PostCommSet;
import synergyhubback.post.dto.request.PostRequest;
import synergyhubback.post.service.PostService;

import java.io.File;
import java.io.IOException;
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
    @GetMapping("/list")
    public ResponseEntity<List<PostEntity>> findPostList( @PageableDefault Pageable pageable) {
        System.out.println("getList stared");
        List<PostEntity> posts= postService.getAllPostList(pageable);
        System.out.println(posts);
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/getAllBoard")
    public ResponseEntity<List<BoardEntity>> getAllBoard() {
        System.out.println("getAllBoard stared");
        List<BoardEntity> boardList = postService.getAllBoard();
        System.out.println("getAllBoard"+boardList);
        return ResponseEntity.ok(boardList);
    }
    @GetMapping("/getLowBoard/{BoardCode}")
    public ResponseEntity<List<LowBoardEntity>> getLowBoard(int BoardCode) {
        System.out.println("getLowBoard stared");
        List<LowBoardEntity> lowBoardList = postService.getLowBoard(BoardCode);
        System.out.println(lowBoardList);
        return ResponseEntity.ok(lowBoardList);
    }
    @PostMapping("/add")
    public String addProduct(@RequestParam("attachFile") List<MultipartFile> attachFile,
                             @RequestParam("postName") String postName,
                             @RequestParam("postCon") String postCon,
                             @RequestParam(value = "postCommSet", defaultValue = "false") PostCommSet postCommSet,
                             @RequestParam(value = "fixStatus", defaultValue = "false") char fixStatus,
                             @RequestParam(value = "noticeStatus", defaultValue = "false") char noticeStatus,
                             Model model) {
        // 상품 정보 저장
        System.out.println("게시글 등록 메소드 작동시작");


        /* 상품 등록하기 */
        PostRequest newPost = new PostRequest();
        newPost.setPostCode("Pst-"+postService.LastPost().getPostCode()+1);
        newPost.setPostName(postName);
        newPost.setPostCon(postCon);
        newPost.setPostCommSet(postCommSet);
        newPost.setFixStatus(fixStatus);
        newPost.setNoticeStatus(noticeStatus);

        PostEntity post= postService.insertPost(newPost);


        /* 경로 설정 */
        String fileUploadDir = POST_FILE_DIR;

        File dir1 = new File(fileUploadDir);

        /* 디렉토리가 없을 경우 생성한다. */
        if (!dir1.exists()) {
            dir1.mkdirs();
        }

        /* 업로드 파일에 대한 정보를 담을 리스트 */
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
                    AttachmentEntity fileInfo = new AttachmentEntity();
                    fileInfo.setAttachOriginal(originalFileName);
                    fileInfo.setAttachUrl(fileUploadDir + "/" + saveFileName);
                    fileInfo.setAttachSave(saveFileName);
                    fileInfo.setAttachSort("POst-" + lastPost.getPostCode());
                    /* 리스트에 파일 정보 저장 */
                    FileList.add(fileInfo);
                }
            }
            /* 파일 리스트를 한 번에 DB에 저장 */
            postService.registFileList(FileList);

            model.addAttribute("message", "파일 업로드에 성공하였습니다.");

        } catch (IOException e) {
            /* 파일 저장 중간에 실패 시, 이전에 저장된 파일 삭제 */

            model.addAttribute("message", "파일 업로드에 실패하였습니다.");
        }
        return "redirect:/admin/post/postList";

    }


}

