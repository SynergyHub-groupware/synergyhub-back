package synergyhubback.post.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import synergyhubback.post.domain.entity.PostEntity;
import synergyhubback.post.dto.request.PostFileDTO;
import synergyhubback.post.dto.request.PostRequest;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query("SELECT p FROM PostEntity p ORDER BY p.PostCode DESC LIMIT 1")
    PostEntity LastPost();


}
