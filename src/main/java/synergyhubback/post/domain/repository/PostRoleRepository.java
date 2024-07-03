package synergyhubback.post.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import synergyhubback.post.domain.entity.PostRoleEntity;

@Repository
public interface PostRoleRepository extends JpaRepository<PostRoleEntity, Long> {
}
