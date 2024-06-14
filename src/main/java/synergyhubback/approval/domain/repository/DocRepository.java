package synergyhubback.approval.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synergyhubback.approval.domain.entity.Document;

import java.util.List;

public interface DocRepository extends JpaRepository<Document, Integer> {

    List<Document> findByEmpCodeOrderByAdReportDateDesc(Integer empCode);
}
