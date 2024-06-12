package synergyhubback.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.post.domain.entity.AttachmentEntity;
import synergyhubback.post.domain.entity.PostEntity;
import synergyhubback.post.domain.repository.AttachmentRepository;
import synergyhubback.post.domain.repository.PostRepository;
import synergyhubback.post.dto.request.PostFileDTO;
import synergyhubback.post.dto.request.PostRequest;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AttachmentRepository attachmentRepository;

    public PostEntity LastPost() {
        return postRepository.LastPost();
    }

    public void registFileList(List<AttachmentEntity> fileList) {
        // 파일 관련 필드 설정
        // PostEntity를 데이터베이스에 저장
        attachmentRepository.saveAll(fileList);


    }

    @Transactional
    public PostEntity insertPost(PostRequest newPost) {
        // PostRequest에서 필요한 필드를 추출하여 PostEntity 객체를 생성
        PostEntity postEntity = new PostEntity();
        postEntity.setPostCode(newPost.getPostCode());
        postEntity.setPostName(newPost.getPostName());
        postEntity.setPostCon(newPost.getPostCon());
        postEntity.setPostCommSet(newPost.getPostCommSet());
        postEntity.setFixStatus(newPost.getFixStatus());
        postEntity.setNoticeStatus(newPost.getNoticeStatus());
        // 나머지 필드들도 동일한 방식으로 설정

        // PostEntity를 데이터베이스에 저장
        return postRepository.save(postEntity);
    }
}
