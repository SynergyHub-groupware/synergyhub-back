package synergyhubback.calendar.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import synergyhubback.calendar.domain.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    // 필요한 추가적인 쿼리 메서드를 여기에 작성할 수 있습니다.
}
