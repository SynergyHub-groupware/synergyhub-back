package synergyhubback.post.domain.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import synergyhubback.post.domain.entity.AttachmentEntity;
import synergyhubback.post.domain.entity.LowBoardEntity;
import synergyhubback.post.domain.entity.PostEntity;
import synergyhubback.post.domain.entity.PostSortEntity;
import synergyhubback.post.domain.repository.PostRepository;
import synergyhubback.post.domain.type.PostCommSet;
import synergyhubback.post.dto.request.PostFileDTO;
import synergyhubback.post.dto.request.PostRequest;
import synergyhubback.post.service.PostService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@SpringBootTest// JPA 관련 테스트 환경 설정
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private PostService postService;

    @Test
    public void testLastPost() {
        // given
        // 필요한 필드 값을 설정합니다.
        // ...
//        PostEntity post = new PostEntity();
//        post.setPostCode("TEST_CODE");
//        post.setPostName("Test Post");
//        post.setPostViewCnt(0);
//        post.setPostDate(LocalDate.now());
//        LowBoardEntity lowBoardEntity = new LowBoardEntity();
//        lowBoardEntity.setLowBoardCode(1); // LowBoardCode 설정
//        lowBoardEntity.setLowBoardName("Test Low Board"); // LowBoardName 설정
//        post.setEmpCode(1);
//        // 다른 필요한 값들도 설정
//        PostSortEntity postSortEntity = new PostSortEntity();
//        postSortEntity.setPsCode(1); // SortCode 설정
//        postSortEntity.setPsName("aaa"); // SortName 설정
//
//        // setLowBoardCode() 메소드 호출
//        post.setLowBoardCode(lowBoardEntity);
//        post.setPsCode(postSortEntity);
//        post.setPostCon("This is a test post content");
//        post.setPostCommSet(PostCommSet.ALLOW_NORMAL);
//        post.setFixStatus('N');
//        post.setNoticeStatus('N');
//
//        // when
//        postRepository.save(post); // 데이터베이스에 엔티티 저장
//        PostEntity lastPost = postRepository.LastPost(); // 메소드 호출
        AttachmentEntity attachment = new AttachmentEntity();
        attachment.setAttachOriginal("test.txt");
        attachment.setAttachUrl("test.txt");
        attachment.setAttachSave("test.txt");
        attachment.setAttachSort("POst-" + "test.txt");

// 리스트 생성 후 attachment 객체 추가
        List<AttachmentEntity> attachmentList = new ArrayList<>();
        attachmentList.add(attachment);

// 리스트를 registFileList 메서드에 전달
        postService.registFileList(attachmentList);

// attachment 객체 출력 (선택 사항)
        System.out.println(attachment);

// attachment가 null이 아닌지 확인
        assertNotNull(attachment);
        // 기대하는 값을 설정하고 확인합니다.
        // assertEquals(expectedValue, actualValue);
    }


}
