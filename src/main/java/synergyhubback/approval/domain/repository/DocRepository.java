package synergyhubback.approval.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import synergyhubback.approval.domain.entity.Document;

import java.util.List;
import java.util.Optional;

public interface DocRepository extends JpaRepository<Document, String> {

    Document findByAdDetail(String code);

    @Query(value = "SELECT * FROM APPROVAL_DOC " +
            "ORDER BY SUBSTRING(AD_CODE, 1, 2), CAST(SUBSTRING(AD_CODE, 3) AS UNSIGNED) DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Document> findTopOrderByAdCodeDesc();

    @Query("SELECT d.adDetail FROM Document d WHERE d.adCode = :adCode")
    String findAdDetailById(String adCode);

    @Query("SELECT d.adTitle FROM Document d WHERE d.adCode = :adCode")
    String findAdTitleById(String adCode);

    @Query("SELECT d.employee.emp_code FROM Document d WHERE d.adCode = :adCode")
    int findEmployeeEmpCodeById(String adCode);

    @Query("SELECT d.adStatus FROM Document d WHERE d.adCode = :adCode")
    String findAdStatusById(String adCode);

    Boolean existsByForm_AfCode(int afCode);
}
