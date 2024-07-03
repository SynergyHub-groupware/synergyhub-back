package synergyhubback.post.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import synergyhubback.auth.util.TokenUtils;
import synergyhubback.common.attachment.AttachmentEntity;
import synergyhubback.common.attachment.AttachmentRepository;
import synergyhubback.employee.domain.entity.Employee;
import synergyhubback.employee.domain.repository.EmployeeRepository;
import synergyhubback.post.domain.entity.*;
import synergyhubback.post.domain.repository.*;
import synergyhubback.post.domain.type.PostCommSet;
import synergyhubback.post.dto.request.BoardRequest;
import synergyhubback.post.dto.request.CommontRequest;
import synergyhubback.post.dto.request.PostRequest;
import synergyhubback.post.dto.request.PostRoleRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private LowBoardRepository lowBoardRepository;
    @Autowired
    private PostRoleRepository postRoleRepository;
    @Autowired
    private BoardRepository boardRepository;

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
        System.out.println(newPost);
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

    public LowBoardEntity boardCreate(BoardRequest boardRequest) {
        System.out.println("boardRequest:" + boardRequest);
        // 보드 코드 가져오기
        int boardCode = boardRequest.getBoardCode();

        // 보드 코드로 BoardEntity 검색
        BoardEntity boardEntity = boardRepository.findByBoardCode(boardCode);

        // 보드 엔티티가 존재하지 않으면 예외 처리
//        if (boardEntity == null) {
//            throw new IllegalArgumentException("해당 코드의 보드가 존재하지 않습니다: " + boardCode);
//        }

        // LowBoardEntity 생성 및 설정
        LowBoardEntity board = new LowBoardEntity();
        board.setLowBoardName(boardRequest.getLowName());
        board.setBoardCode(boardEntity);

        // LowBoardEntity 저장
        return lowBoardRepository.save(board);
    }

    public void createRoles(List<PostRoleRequest> updatedRoles) {
        for (PostRoleRequest role : updatedRoles) {
            PostRoleEntity postRole = new PostRoleEntity();
            postRole.setPrWriteRole(role.getPrWriteRole());
            LowBoardEntity lowBoardEntity = new LowBoardEntity(); // Assuming constructor with ID
            lowBoardEntity.setLowBoardCode(role.getLowCode());
            postRole.setLowCode(lowBoardEntity);
            Employee employee = new Employee();
            employee.setEmp_code(role.getEmpCode());

            postRole.setEmpCode(employee);
            postRole.setPrAdmin(role.getPrAdmin());
            postRoleRepository.save(postRole);
        }

    }
//    @Transactional
//    public List<PostRoleRequest> postRoleCreate(Map<String, PostRoleRequest> updatedRoles) {
//        List<PostRoleEntity> entitiesToSave = new ArrayList<>();
//        System.out.println("start");
//        System.out.println("start");
//        System.out.println("start");
//        System.out.println("start");
//        System.out.println("start");
//        System.out.println("start");
//        System.out.println("start");
//        System.out.println("start");
//        // Create and populate readpostRole
////        PostRoleEntity readpostRole = new PostRoleEntity();
////        readpostRole.setPrAdmin(readPostRoleRequest.getPrAdmin());
////        LowBoardEntity lowBoardEntity = new LowBoardEntity(); // Assuming constructor with ID
////        lowBoardEntity.setLowBoardCode(readPostRoleRequest.getLowCode());
////        readpostRole.setLowCode(lowBoardEntity); // Assuming constructor with ID
////        Employee employee = new Employee();
////        employee.setEmp_code(readPostRoleRequest.getEmpCode());
////        readpostRole.setEmpCode(employee); // Assuming constructor with ID
////        readpostRole.setPrWriteRole(readPostRoleRequest.getPrWriteRole());
////        entitiesToSave.add(readpostRole);
////
////        // Create and populate writepostRole
////        PostRoleEntity writepostRole = new PostRoleEntity();
////        writepostRole.setPrAdmin(writePostRoleRequest.getPrAdmin());
////        LowBoardEntity lowBoardEntity2 = new LowBoardEntity(); // Assuming constructor with ID
////        lowBoardEntity2.setLowBoardCode(writePostRoleRequest.getLowCode());
////        writepostRole.setLowCode(lowBoardEntity2); // Assuming constructor with ID
////        Employee employee2 = new Employee();
////        employee.setEmp_code(writePostRoleRequest.getEmpCode());
////        writepostRole.setEmpCode(employee2); // Assuming constructor with ID
////        writepostRole.setPrWriteRole(writePostRoleRequest.getPrWriteRole());
////        entitiesToSave.add(writepostRole);
////
////        // Create and populate adminpostRole
////        PostRoleEntity adminpostRole = new PostRoleEntity();
////        adminpostRole.setPrAdmin(adminPostRoleRequest.getPrAdmin());
////        LowBoardEntity lowBoardEntity3 = new LowBoardEntity(); // Assuming constructor with ID
////        lowBoardEntity3.setLowBoardCode(adminPostRoleRequest.getLowCode());
////        adminpostRole.setLowCode(lowBoardEntity3); // Assuming constructor with ID
////        Employee employee3 = new Employee();
////        employee3.setEmp_code(adminPostRoleRequest.getEmpCode());
////        adminpostRole.setEmpCode(employee3); // Assuming constructor with ID
////        adminpostRole.setPrWriteRole(adminPostRoleRequest.getPrWriteRole());
////        entitiesToSave.add(adminpostRole);
//
//        PostRoleEntity postRole = new PostRoleEntity();
//        postRole.setPrAdmin(updatedRoles.get("readPostRole").getPrAdmin());
//        postRole.setPrWriteRole(updatedRoles.get("readPostRole").getPrWriteRole());
//        postRole.setLowCode(updatedRoles.get());
//
//        System.out.println(entitiesToSave);
//        // Save entities to database
//        List<PostRoleEntity> savedEntities = postRoleRepository.saveAll(entitiesToSave);
//
//        // Convert entities back to DTOs and return
//        List<PostRoleRequest> response = new ArrayList<>();
//        for (PostRoleEntity entity : savedEntities) {
//            PostRoleRequest dto = new PostRoleRequest();
//            dto.setPrWriteRole(entity.getPrWriteRole());
//            dto.setLowCode(entity.getLowCode().getLowBoardCode()); // Assuming getId method exists
//            dto.setEmpCode(entity.getEmpCode().getEmp_code()); // Assuming getId method exists
//            dto.setPrAdmin(entity.getPrAdmin());
//            response.add(dto);
//        }
//
//        return response;
//    }
}

