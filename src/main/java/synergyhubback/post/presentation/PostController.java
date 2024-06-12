package synergyhubback.post.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import synergyhubback.post.domain.entity.AttachmentEntity;
import synergyhubback.post.domain.entity.PostEntity;
import synergyhubback.post.dto.request.PostFileDTO;
import synergyhubback.post.dto.request.PostRequest;
import synergyhubback.post.service.PostService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    @Value("${file.post-dir}")
    private String POST_FILE_DIR;

    private final PostService postService;


    @PostMapping("/add")
    public String addProduct(@RequestParam List<MultipartFile> attachFile,
                             PostEntity Post, Model model) {
        // 상품 정보 저장



        /* 상품 등록하기 */
        PostRequest newPost = new PostRequest();
        newPost.setPostCode("Pst-"+postService.LastPost().getPostCode()+1);
        newPost.setPostName(Post.getPostName());
        newPost.setPostCon(Post.getPostCon());
        newPost.setPostCommSet(Post.getPostCommSet());
        newPost.setFixStatus(Post.getFixStatus());
        newPost.setNoticeStatus(Post.getNoticeStatus());

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

