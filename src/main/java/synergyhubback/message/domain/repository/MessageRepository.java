package synergyhubback.message.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import synergyhubback.message.domain.entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {

    @Query("SELECT m FROM Message m WHERE m.empRev.emp_code = :empCode and m.storCode.storCode = 1")
    List<Message> findByEmpRev_EmpCode(int empCode);

    @Query("SELECT m FROM Message m WHERE m.empSend.emp_code = :empCode AND m.storCode.storCode = 1")
    List<Message> findByEmpSend_EmpCode(int empCode);

    @Query("SELECT m FROM Message m WHERE (m.empSend.emp_code = :empCode OR m.empRev.emp_code = :empCode) AND m.storCode.storCode = 5")
    List<Message> findByBin_EmpCode(int empCode);

    @Query("SELECT m FROM Message m WHERE (m.empSend.emp_code = :empCode OR m.empRev.emp_code = :empCode) AND m.storCode.storCode = 2")
    List<Message> findByImp_EmpCode(int empCode);

    @Query("SELECT m FROM Message m WHERE (m.empSend.emp_code = :empCode OR m.empRev.emp_code = :empCode) AND m.storCode.storCode = 3")
    List<Message> findByWork_EmpCode(int empCode);

    @Query("SELECT m FROM Message m WHERE m.msgCode = :msgCode")
    Message findByMsgCode(String msgCode);

    @Query("SELECT m FROM Message m WHERE m.msgCode = :msgCode")
    Message findSendMsgByMsgCode(String msgCode);
}
