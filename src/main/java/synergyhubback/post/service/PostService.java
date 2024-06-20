package synergyhubback.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.common.attachment.AttachmentEntity;
import synergyhubback.common.attachment.AttachmentRepository;
import synergyhubback.post.domain.entity.*;
import synergyhubback.post.domain.repository.PostRepository;
import synergyhubback.post.dto.request.PostRequest;

import java.time.LocalDate;
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
        // PostRequest 에서 필요한 필드를 추출하여 PostEntity 객체를 생성
        PostEntity postEntity = new PostEntity();
        postEntity.setPostCode(newPost.getPostCode());
        postEntity.setPostName(newPost.getPostName());
        postEntity.setPostCon(newPost.getPostCon());
        postEntity.setPostCommSet(newPost.getPostCommSet());
        postEntity.setFixStatus(newPost.getFixStatus());
        postEntity.setNoticeStatus(newPost.getNoticeStatus());
        postEntity.setLowBoardCode(newPost.getLowBoardCode());
        postEntity.setPsCode(newPost.getPsCode());
        LocalDate now = LocalDate.now();
        postEntity.setPostDate(now);
        // 나머지 필드들도 동일한 방식으로 설정

        // PostEntity를 데이터베이스에 저장
        return postRepository.save(postEntity);
    }

    public List<PostEntity> getAllPostList(Pageable pageable) {
      return   postRepository.findAll(pageable).getContent();
    }

    public List<BoardEntity> getAllBoard() {
        return postRepository.getAllBoard();
    }

    public List<LowBoardEntity> getLowBoard(Integer boardCode) {
        return postRepository.getLowBoard(boardCode);
    }

    public List<PostSortEntity> getAllPostSortList() {
        return postRepository.getAllPostSortList();
    }

    public List<PostEntity> InboardList(Pageable pageable,Integer lowBoardCode) {
        return postRepository.InboardList(pageable,lowBoardCode);
    }

    public List<PostEntity> InboardPinList(Pageable pageable, Integer lowBoardCode) {
        return postRepository.InboardPinList(pageable,lowBoardCode);
    }

    public PostEntity getDetail(String postCode) {
        return postRepository.getDetail(postCode);
    }

    public List<AttachmentEntity> getFile(String postCode) {
        return attachmentRepository.getFile(postCode);
    }

    public List<CommentEntity> getCommentList(String postCode) {
        return postRepository.getCommentList(postCode);
    }
}
