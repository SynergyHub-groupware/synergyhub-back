package synergyhubback.post.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import synergyhubback.auth.util.TokenUtils;
import synergyhubback.common.attachment.AttachmentEntity;
import synergyhubback.common.attachment.AttachmentRepository;
import synergyhubback.employee.domain.entity.Employee;
import synergyhubback.employee.domain.repository.EmployeeRepository;
import synergyhubback.post.domain.entity.*;
import synergyhubback.post.domain.repository.CommentRepository;
import synergyhubback.post.domain.repository.PostRepository;
import synergyhubback.post.domain.type.PostCommSet;
import synergyhubback.post.dto.request.CommontRequest;
import synergyhubback.post.dto.request.PostRequest;
import synergyhubback.post.dto.response.CommonResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

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
        List<PostEntity> posts = postRepository.AllPostList(pageable);

        // 새로운 ArrayList로 복사
        List<PostEntity> modifiableList = new ArrayList<>(posts);

        // 리스트를 원하는 방식으로 정렬 (예시: ID 오름차순)
        modifiableList.sort(Comparator.comparing(PostEntity::getPostCode));

        return modifiableList;
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

    public List<PostEntity> InboardList(Pageable pageable, Integer lowBoardCode) {
        return postRepository.InboardList(pageable, lowBoardCode);
    }

    public List<PostEntity> InboardPinList(Pageable pageable, Integer lowBoardCode) {
        return postRepository.InboardPinList(pageable, lowBoardCode);
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

    @Transactional
    public CommontRequest commentAdd(String token, CommontRequest commontRequest) {
        String jwtToken = TokenUtils.getToken(token);
        String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
        int empCode = Integer.parseInt(tokenEmpCode);
        CommentEntity comment = new CommentEntity();

        Employee employee = employeeRepository.findByEmpCode(empCode);
        if (employee == null) {
            throw new EntityNotFoundException("Employee not found");
        }

        comment.setEmpCode(employee);


        comment.setCommCon(commontRequest.getCommCon());

        PostEntity postEntity = postRepository.findByPostCode(commontRequest.getPostCode());
        if (postEntity == null) {
            throw new EntityNotFoundException("Post not found");
        }
        comment.setPostCode(postEntity);
        comment.setCommStatus(commontRequest.getCommStatus());
        LocalDateTime now = LocalDateTime.now();
        comment.setCommDate(now);
        commentRepository.save(comment);
        CommontRequest response = new CommontRequest();
        return response;
    }

    public List<LowBoardEntity> getAllLowBoard() {
        return postRepository.getAllLowBoard();
    }

    public List<PostEntity> postSearch(String searchWord) {
        return postRepository.postSearch(searchWord);
    }

    public PostEntity findByPostCode(String postCode) {
        return postRepository.findByPostCode(postCode);
    }

    @Transactional
    public Integer deleteComment(String commCode) {
        return postRepository.deleteComment(commCode);
    }

    @Transactional
    public Integer updateComment(String commCode, String commCon) {
        System.out.println(commCode);
        System.out.println(commCon);
        return postRepository.updateComment(commCode, commCon);
    }

    @Transactional
    public Integer postUpdate(String postCode, String postName, String postCon, int lowBoardCode, int postCommSet, char fixStatus, char noticeStatus, int psCode) {
        PostCommSet commSet = PostCommSet.fromValue(postCommSet);

        return postRepository.postUpdate(postCode, postName, postCon, lowBoardCode, commSet, fixStatus, noticeStatus, psCode);
    }

    @Transactional
    public void FileUpdate(List<MultipartFile> attachFile, String postCode) {
        postRepository.deleteFile(postCode);


    }

    @Transactional
    public Integer postDelete(String postCode) {
        return postRepository.postDelete(postCode);
    }

    @Transactional
    public List<PostRequest> ReadyPost(int empCode) {
        List<PostEntity> postEntities = postRepository.ReadyPost(empCode);
        return postEntities.stream()
                .map(PostEntity::toPostRequest)
                .collect(Collectors.toList());
    }
}
