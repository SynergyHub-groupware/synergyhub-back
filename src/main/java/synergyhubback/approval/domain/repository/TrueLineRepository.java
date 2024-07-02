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
            ")" +
            "AND t.employee.emp_code = :empCode " +
            "AND (d.adStatus = '대기' OR d.adStatus = '진행중') " +
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
            "AND d.adStatus = '완료' " +
            "ORDER BY d.adReportDate DESC, " +
            "SUBSTRING(d.adCode, 1, 2), CAST(SUBSTRING(d.adCode, 3) AS Integer) DESC")
    List<TrueLine> findCompleteReceiveList(Integer empCode);

    @Query("SELECT t FROM TrueLine t " +
            "JOIN t.document d " +
            "JOIN d.employee e " +
            "JOIN d.form f " +
            "WHERE t.document.adCode IN (" +
            "    SELECT t2.document.adCode " +
            "    FROM TrueLine t2 " +
            "    WHERE t2.employee.emp_code = :empCode " +
            ")" +
            "AND t.talStatus = '반려' " +
            "ORDER BY d.adReportDate DESC, " +
            "SUBSTRING(d.adCode, 1, 2), CAST(SUBSTRING(d.adCode, 3) AS Integer) DESC")
    List<TrueLine> findReturnReceiveList(Integer empCode);

    @Query("SELECT t FROM TrueLine t " +
            "JOIN t.document d " +
            "JOIN d.employee e " +
            "JOIN d.form f " +
            "WHERE (t.employee.emp_code = :empCode AND t.talRole = '열람' AND d.adStatus = '완료') " +
            "OR (t.employee.emp_code = :empCode AND t.talRole = '참조' AND d.adStatus != '임시저장')")
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


//    @Query(value = "SELECT ab.AB_code, ab.AB_NAME, s.AS_CODE, ad.AD_CODE, ad.AD_TITLE, af.AF_NAME, tal.EMP_CODE, ei.emp_name, tal.TAL_DATE " +
//            "FROM approval_box ab " +
//            "JOIN approval_storage s USING (ab_code) " +
//            "JOIN approval_doc ad USING (ad_code) " +
//            "JOIN true_app_line tal ON ad.ad_code = tal.ad_code " +
//            "JOIN employee_info ei ON tal.EMP_CODE = ei.EMP_CODE " +
//            "JOIN approval_form af ON ad.AF_CODE = af.AF_CODE " +
//            "WHERE ab.ab_code = :abCode " +
//            "AND tal.tal_order = ( " +
//            "    SELECT MAX(tal_order) " +
//            "    FROM true_app_line " +
//            "    WHERE ad_code = ad.ad_code " +
//            "      AND tal_status = '승인' " +
//            ")", nativeQuery = true)
//    List<Object[]> findDocListInStorage(int abCode);
}
