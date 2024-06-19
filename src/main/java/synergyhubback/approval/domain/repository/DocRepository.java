package synergyhubback.approval.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import synergyhubback.approval.domain.entity.Document;

import java.util.List;
import java.util.Optional;

public interface DocRepository extends JpaRepository<Document, Integer> {

    Document findByAdDetail(String code);

    @Query(value = "SELECT * FROM APPROVAL_DOC " +
            "ORDER BY SUBSTRING(AD_CODE, 1, 2), CAST(SUBSTRING(AD_CODE, 3) AS UNSIGNED) DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Document> findTopOrderByAdCodeDesc();
}
