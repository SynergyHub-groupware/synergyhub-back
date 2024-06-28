package synergyhubback.calendar.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import synergyhubback.calendar.domain.entity.Task;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    // 필요한 추가적인 쿼리 메서드를 여기에 작성할 수 있습니다.
    @Query(value = "SELECT * FROM TASK ORDER BY CAST(SUBSTRING(TASK_CODE, 3) AS UNSIGNED) DESC LIMIT 1", nativeQuery = true)
    Optional<Task> findTopByOrderByIdDesc();
}
