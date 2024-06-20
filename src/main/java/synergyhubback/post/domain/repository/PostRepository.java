package synergyhubback.post.domain.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import synergyhubback.post.domain.entity.*;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query("SELECT p FROM PostEntity p ORDER BY p.PostCode DESC LIMIT 1")
    PostEntity LastPost();

    @Query("SELECT p FROM LowBoardEntity p WHERE p.BoardCode.BoardCode = :boardCode")
    List<LowBoardEntity> getLowBoard(@Param("boardCode") Integer boardCode);

    @Query("select b from BoardEntity b")
    List<BoardEntity> getAllBoard();

    @Query("select p from PostSortEntity p ")
    List<PostSortEntity> getAllPostSortList();

    @Query("select p from PostEntity p where p.LowBoardCode.LowBoardCode=:lowCode")
    List<PostEntity> InboardList(Pageable pageable,@Param("lowCode") Integer lowCode);

    @Query("SELECT p FROM PostEntity p WHERE p.FixStatus = 'Y' AND p.LowBoardCode.LowBoardCode = :lowCode ORDER BY p.PostCode DESC")
    List<PostEntity> InboardPinList(Pageable pageable,@Param("lowCode") Integer lowCode);

    @Query("SELECT p FROM PostEntity p WHERE p.PostCode=:postCode ")
    PostEntity getDetail(@Param("postCode") String postCode);

    @Query("select c from CommentEntity c where c.PostCode.PostCode = :postCode")
    List<CommentEntity> getCommentList(@Param("postCode") String postCode);
}
