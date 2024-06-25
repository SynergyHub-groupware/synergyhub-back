package synergyhubback.approval.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import synergyhubback.approval.domain.entity.TrueLine;

import java.util.List;

public interface TrueLineRepository extends JpaRepository<TrueLine, Integer> {

    @Query("SELECT t FROM TrueLine t " +
            "JOIN t.document d " +
            "JOIN d.employee e " +
            "JOIN d.form f " +
            "WHERE t.talOrder = (" +
            "    SELECT MIN(t2.talOrder) " +
            "    FROM TrueLine t2 " +
            "    WHERE t2.document = d " +
            "    AND t2.talStatus = '미결재' " +
            ")" +
            "AND d.adStatus = :status " +
            "AND e.emp_code = :empCode " +
            "ORDER BY d.adReportDate DESC, " +
            "SUBSTRING(d.adCode, 1, 2), CAST(SUBSTRING(d.adCode, 3) AS Integer) DESC")
    List<TrueLine> findTrueLineWithPendingStatus(Integer empCode, String status);
    @Query("SELECT t FROM TrueLine t " +
            "JOIN t.employee e " +
            "JOIN e.department d " +
            "JOIN e.title tt " +
            "WHERE t.document.adCode = :adCode")
    List<TrueLine> findViewLineList(String adCode);

    void deleteByDocument_AdCode(String adCode);

    List<TrueLine> findByDocument_AdCode(String adCode);

    @Query("SELECT t FROM TrueLine t " +
            "JOIN t.document d " +
            "JOIN d.employee e " +
            "JOIN d.form f " +
            "WHERE t.talOrder = (" +
            "    SELECT MIN(t2.talOrder) " +
            "    FROM TrueLine t2 " +
            "    WHERE t2.document = d " +
            "    AND t2.talStatus = '미결재' " +
            ")" +
            "AND t.employee.emp_code = :empCode " +
            "ORDER BY d.adReportDate DESC, " +
            "SUBSTRING(d.adCode, 1, 2), CAST(SUBSTRING(d.adCode, 3) AS Integer) DESC")
    List<TrueLine> findWaitingReceiveList(Integer empCode);

    @Query("SELECT t FROM TrueLine t WHERE t.employee.emp_code = :empCode AND t.document.adCode = :adCode")
    TrueLine findByEmployee_Emp_codeAndDocument_AdCode(Integer empCode, String adCode);

    @Query("SELECT t FROM TrueLine t " +
            "JOIN t.document d " +
            "JOIN d.employee e " +
            "JOIN d.form f " +
            "WHERE t.talOrder = (" +
            "    SELECT MAX(t2.talOrder) " +
            "    FROM TrueLine t2 " +
            "    WHERE t2.document = d " +
            "    AND t2.talStatus = '승인' " +
            ")" +
            "AND t.employee.emp_code = :empCode " +
            "ORDER BY d.adReportDate DESC, " +
            "SUBSTRING(d.adCode, 1, 2), CAST(SUBSTRING(d.adCode, 3) AS Integer) DESC")
    List<TrueLine> findCompleteReceiveList(Integer empCode);
}
