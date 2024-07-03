package synergyhubback.post.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import synergyhubback.post.domain.entity.CommentEntity;
import synergyhubback.post.domain.entity.LowBoardEntity;

@Repository
public interface LowBoardRepository extends JpaRepository<LowBoardEntity, Long> {
}
