package synergyhubback.post.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import synergyhubback.post.domain.entity.BoardEntity;
import synergyhubback.post.domain.entity.LowBoardEntity;
import synergyhubback.post.domain.entity.PostEntity;
import synergyhubback.post.dto.request.PostFileDTO;
import synergyhubback.post.dto.request.PostRequest;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query("SELECT p FROM PostEntity p ORDER BY p.PostCode DESC LIMIT 1")
    PostEntity LastPost();

    @Query("SELECT p FROM LowBoardEntity p WHERE p.BoardCode = :boardCode")
    List<LowBoardEntity> getLowBoard(int boardCode);

    @Query("select b from BoardEntity b")
    List<BoardEntity> getAllBoard();
}
