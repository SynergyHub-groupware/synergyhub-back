package synergyhubback.approval.domain.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import synergyhubback.approval.domain.entity.Form;
import synergyhubback.approval.domain.entity.Line;

public interface FormRepository extends JpaRepository<Form, Integer> {
}
