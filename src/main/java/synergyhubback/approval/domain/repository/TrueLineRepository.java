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
            "    AND t2.talOrder > 0 " +
            ")" +
            "AND d.adStatus = :status " +
            "AND e.emp_code = :empCode " +
            "ORDER BY d.adReportDate DESC, " +
            "SUBSTRING(d.adCode, 1, 2), CAST(SUBSTRING(d.adCode, 3) AS Integer) DESC")
    List<TrueLine> findWaitingSendList(Integer empCode, String status);

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
            "AND d.adStatus = '완료' " +
            "AND e.emp_code = :empCode " +
            "ORDER BY d.adReportDate DESC, " +
            "SUBSTRING(d.adCode, 1, 2), CAST(SUBSTRING(d.adCode, 3) AS Integer) DESC")
    List<TrueLine> findCompleteSendList(Integer empCode);

    @Query("SELECT t FROM TrueLine t " +
            "JOIN t.document d " +
            "JOIN d.employee e " +
            "JOIN d.form f " +
            "WHERE d.employee.emp_code = :empCode " +
            "AND t.talStatus = '반려' " +
            "ORDER BY d.adReportDate DESC, " +
            "SUBSTRING(d.adCode, 1, 2), CAST(SUBSTRING(d.adCode, 3) AS Integer) DESC")
    List<TrueLine> findReturnSendList(Integer empCode);

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
            "    AND t2.talOrder > 0 " +
            ")" +
            "AND t.employee.emp_code = :empCode " +
            "AND (d.adStatus = '대기' OR d.adStatus = '진행중') " +
            "ORDER BY d.adReportDate DESC, " +
            "SUBSTRING(d.adCode, 1, 2), CAST(SUBSTRING(d.adCode, 3) AS Integer) DESC")
    List<TrueLine> findWaitingReceiveList(Integer empCode);

    @Query("SELECT t FROM TrueLine t WHERE t.employee.emp_code = :empCode AND t.document.adCode = :adCode")
    TrueLine findByEmployee_Emp_codeAndDocument_AdCode(Integer empCode, String adCode);

    @Query("SELECT t FROM TrueLine t " +
            "JOIN t.document ad " +
            "JOIN ad.employee ei " +
            "JOIN ad.form af " +
            "WHERE t.talOrder = (" +
            "   SELECT MAX(t3.talOrder) FROM TrueLine t3 " +
            "   WHERE t3.document.adCode = t.document.adCode " +
            "   AND t3.talStatus = '승인' " +
            "   AND t3.document.adCode IN (" +
            "       SELECT DISTINCT t4.document.adCode FROM TrueLine t4 " +
            "       WHERE t4.employee.emp_code = :empCode" +
            "   )" +
            ")" +
            "AND ad.adStatus = '완료' " +
            "ORDER BY t.talDate DESC, " +
            "SUBSTRING(ad.adCode, 1, 2), CAST(SUBSTRING(ad.adCode, 3) AS Integer) DESC")
    List<TrueLine> findCompleteReceiveList(Integer empCode);

// 본인이 포함된 반려된 결재 조회
//    @Query("SELECT t FROM TrueLine t " +
//            "JOIN t.document d " +
//            "JOIN d.employee e " +
//            "JOIN d.form f " +
//            "WHERE t.document.adCode IN (" +
//            "    SELECT t2.document.adCode " +
//            "    FROM TrueLine t2 " +
//            "    WHERE t2.employee.emp_code = :empCode " +
//            ")" +
//            "AND t.talStatus = '반려' " +
//            "ORDER BY d.adReportDate DESC, " +
//            "SUBSTRING(d.adCode, 1, 2), CAST(SUBSTRING(d.adCode, 3) AS Integer) DESC")3

    // 본인 순서 전에 반려되면 조회되지 않게 처리 (본인은 승인했지만, 뒤에 반려됐으면? 조회 포함)
    @Query("SELECT t FROM TrueLine t " +
            "JOIN t.document ad " +
            "JOIN ad.employee ei " +
            "JOIN ad.form af " +
            "WHERE ad.adStatus = '반려' " +
            "AND t.talStatus = '반려' " +
            "AND t.document.adCode IN (" +
            "SELECT t2.document.adCode FROM TrueLine t2 " +
            "WHERE t2.talStatus IN ('승인', '반려') " +
            "AND t2.employee.emp_code = :empCode)" +
            "ORDER BY t.talDate DESC, " +
            "SUBSTRING(ad.adCode, 1, 2), CAST(SUBSTRING(ad.adCode, 3) AS Integer) DESC")
    List<TrueLine> findReturnReceiveList(Integer empCode);

    @Query("SELECT t FROM TrueLine t " +
            "JOIN t.document d " +
            "JOIN d.employee e " +
            "JOIN d.form f " +
            "WHERE d.adCode IN (" +
            "    SELECT d2.adCode FROM TrueLine t2 " +
            "    JOIN t2.document d2 " +
            "    WHERE (t2.employee.emp_code = :empCode AND t2.talRole = '열람' AND d2.adStatus = '완료') " +
            "    OR (t2.employee.emp_code = :empCode AND t2.talRole = '참조' AND d2.adStatus != '임시저장')" +
            ") " +
            "AND t.talOrder = (" +
            "    SELECT MAX(t3.talOrder) FROM TrueLine t3 " +
            "    WHERE t3.document.adCode = d.adCode " +
            "    AND t3.talStatus != '미결재'" +
            ") " +
            "ORDER BY d.adReportDate DESC, " +
            "SUBSTRING(d.adCode, 1, 2), CAST(SUBSTRING(d.adCode, 3) AS Integer) DESC")
    List<TrueLine> findReferenceReceiveList(Integer empCode);

    @Query("SELECT t FROM TrueLine t " +
            "WHERE t.document.adCode = :adCode " +
            "AND t.talOrder > (" +
            "SELECT t2.talOrder " +
            "FROM TrueLine t2 " +
            "WHERE t2.document.adCode = :adCode AND t2.employee.emp_code = :empCode) " +
            "AND t.talStatus = '미결재' " +
            "ORDER BY t.talOrder")
    List<TrueLine> findAfterList(Integer empCode, String adCode);

    @Query("SELECT t FROM TrueLine t " +
            "JOIN t.document d " +
            "JOIN d.employee e " +
            "JOIN d.form f " +
            "JOIN ApprovalStorage s ON d = s.document " +
            "JOIN ApprovalBox b ON s.approvalBox = b " +
            "WHERE t.talOrder = (" +
            "    SELECT MAX(t2.talOrder) " +
            "    FROM TrueLine t2 " +
            "    WHERE t2.document = d " +
            "    AND t2.talStatus = '승인' " +
            ")" +
            "AND b.abCode = :abCode " +
            "ORDER BY d.adReportDate DESC, " +
            "SUBSTRING(d.adCode, 1, 2), CAST(SUBSTRING(d.adCode, 3) AS Integer) DESC")
    List<TrueLine> findDocListInStorage(int abCode);

}
