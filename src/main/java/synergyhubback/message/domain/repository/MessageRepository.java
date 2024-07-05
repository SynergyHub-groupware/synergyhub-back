package synergyhubback.message.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import synergyhubback.message.domain.entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {

    @Query("SELECT m FROM Message m WHERE m.empRev.emp_code = :empCode and m.revStor.storCode = 1")
    List<Message> findByEmpRev_EmpCode(int empCode);

    @Query("SELECT m FROM Message m WHERE m.empSend.emp_code = :empCode AND m.sendStor.storCode = 1")
    List<Message> findByEmpSend_EmpCode(int empCode);

    @Query("SELECT m FROM Message m WHERE (m.empRev.emp_code = :empCode AND m.revStor.storCode = 5) OR (m.empSend.emp_code = :empCode AND m.sendStor.storCode = 5)")
    List<Message> findByBin_EmpCode(int empCode);

    @Query("SELECT m FROM Message m WHERE ((m.empSend.emp_code = :empCode OR m.empRev.emp_code = :empCode) AND m.revStor.storCode = 2) OR ((m.empSend.emp_code = :empCode OR m.empRev.emp_code = :empCode) AND m.sendStor.storCode = 2)")
    List<Message> findByImp_EmpCode(int empCode);

    @Query("SELECT m FROM Message m WHERE ((m.empSend.emp_code = :empCode OR m.empRev.emp_code = :empCode) AND m.revStor.storCode = 3) OR ((m.empSend.emp_code = :empCode OR m.empRev.emp_code = :empCode) AND m.sendStor.storCode = 3)")
    List<Message> findByWork_EmpCode(int empCode);

    @Query("SELECT m FROM Message m WHERE m.msgCode = :msgCode")
    Message findByMsgCode(String msgCode);

    @Query("SELECT m FROM Message m WHERE m.msgCode = :msgCode")
    Message findSendMsgByMsgCode(String msgCode);

    @Query("SELECT MAX(CAST(SUBSTRING(m.msgCode, 3) AS INTEGER )) AS maxMsgNumber FROM Message m WHERE m.msgCode LIKE 'MS%'")
    String findLastMsgCode();

    @Query("SELECT m FROM Message m WHERE (m.empSend.emp_code = :empCode OR m.empRev.emp_code = :empCode) AND m.sendStor.storCode = 4")
    List<Message> findByTemp_empCode(int empCode);
}
