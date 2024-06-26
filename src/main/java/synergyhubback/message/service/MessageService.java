package synergyhubback.message.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.employee.domain.entity.Employee;
import synergyhubback.message.domain.entity.Message;
import synergyhubback.message.domain.entity.Storage;
import synergyhubback.message.domain.repository.MessageRepository;
import synergyhubback.message.domain.repository.MsgEmpRepository;
import synergyhubback.message.domain.repository.StorageRepository;
import synergyhubback.message.dto.request.CreateMsgRequest;
import synergyhubback.message.dto.response.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service("messageService")
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final StorageRepository storageRepository;
    private final MsgEmpRepository msgEmpRepository;

    /* emp_code를 찾아서 받은 쪽지 리스트 조회 */
    public List<ReceiveResponse> getReceiveMessage(int empCode) {

        List<Message> receiveList = messageRepository.findByEmpRev_EmpCode(empCode);

        System.out.println("receiveList : " + receiveList.size());

        return receiveList.stream()
                .map(ReceiveResponse::getReceiveMessage)
                .collect(Collectors.toList());
    }

    /* emp_code를 찾아서 보낸 쪽지 리스트 조회 */
    public List<SendResponse> getSendMessage(int empCode) {

        List<Message> sendList = messageRepository.findByEmpSend_EmpCode(empCode);

        System.out.println("sendList : " + sendList.size());

        return sendList.stream()
                .map(SendResponse::getSendMessage)
                .collect(Collectors.toList());
    }

    /* 휴지통 리스트 전체 조회*/
    public List<BinResponse> getBinMessage(int empCode) {

        List<Message> binList = messageRepository.findByBin_EmpCode(empCode);

        System.out.println("binList : " + binList.size());

        return binList.stream()
                .map(BinResponse::getBinMessage)
                .collect(Collectors.toList());
    }

    /* 받은 쪽지 휴지통 PUT 업데이트 로직 */
    @Transactional
    public void RevMsgDel(String msgCode, int storCode) {
        Message message = messageRepository.findById(msgCode)
                .orElseThrow(() -> new IllegalArgumentException("message not found with msgCode : " + msgCode));

        Storage storage = storageRepository.findById(storCode)
                .orElseThrow(() -> new IllegalArgumentException("storage not found with storCode : " + storCode ));

        message.setRevStor(storage);

        messageRepository.save(message);
    }

    /* 보낸 쪽지 휴지통 PUT 업데이트 로직 */
    @Transactional
    public void SendMsgDel(String msgCode, int storCode) {
        Message message = messageRepository.findById(msgCode)
                .orElseThrow(() -> new IllegalArgumentException("message not found with msgCode : " + msgCode));

        Storage storage = storageRepository.findById(storCode)
                .orElseThrow(() -> new IllegalArgumentException("storage not found with storCode : " + storCode));

        message.setSendStor(storage);

        messageRepository.save(message);
    }

    /* 중요 보관함 전체 조회 로직 */
    public List<ImpResponse> getImpMessage(int empCode) {

        List <Message> impList = messageRepository.findByImp_EmpCode(empCode);

        System.out.println("impList : " + impList.size());

        return impList.stream()
                .map(ImpResponse::getImpMessage)
                .collect(Collectors.toList());
    }

    /* 업무 보관함 전체 조회 로직 */
    public List<WorkResponse> getWorkMessage(int empCode) {

        List<Message> workList = messageRepository.findByWork_EmpCode(empCode);

        System.out.println("workList : " + workList.size());

        return workList.stream()
                .map(WorkResponse::getWorkMessage)
                .collect(Collectors.toList());
    }
    public List<TempResponse> getTempMessage(int empCode) {

        List<Message> tempList = messageRepository.findByTemp_empCode(empCode);

        System.out.println("tempList = " + tempList);

        return tempList.stream()
                .map(TempResponse::getTempMessage)
                .collect(Collectors.toList());
    }

    /* Rev Msg By MsgCode */
    public Message findMsgByMsgCode(String msgCode) {
        return messageRepository.findByMsgCode(msgCode);
    }

    /* Send Msg By MsgCode */
    public Message findSendMsgByMsgCode(String msgCode) {
        return messageRepository.findSendMsgByMsgCode(msgCode);
    }

    /* old msgCode를 가져와서 비교 후 new msgCode로 교체 */
    private String newMsgCode(String lastMsgCode) {

        if (lastMsgCode == null || !lastMsgCode.matches("\\d+")) {
            return "MS1";
        }

        int lastNum = Integer.parseInt(lastMsgCode);

        return "MS" + (lastNum + 1);
    }

//    /* Send Msg (Insert) */
    @Transactional
    public void createMessage(String msgTitle, String msgCon, String msgStatus, String emerStatus, Employee empRev, Employee empSend, Storage revStor, Storage sendStor) {

        String lastMsgCode = messageRepository.findLastMsgCode();
        String newMsgCode = newMsgCode(lastMsgCode);

        System.out.println("lastMsgCode = " + lastMsgCode);
        System.out.println("newMsgCode = " + newMsgCode);
        System.out.println("msgCon = " + msgCon);
        System.out.println("empRev = " + empRev);
        System.out.println("empSend = " + empSend);
        System.out.println("revStor = " + revStor);
        System.out.println("sendStor = " + sendStor);

        Message message = Message.create(
                newMsgCode,
                LocalDate.now(),
                msgTitle,
                msgCon,
                msgStatus,
                emerStatus
        );

        message.setEmpRev(empRev);
        message.setEmpSend(empSend);
        message.setRevStor(revStor);
        message.setSendStor(sendStor);

        System.out.println("message.getEmpRev() = " + message.getEmpRev());

        messageRepository.save(message);
    }

    /* Temp Create Msg */
    public void createTemp(String msgTitle, String msgCon, String msgStatus, String emerStatus, Employee empRev, Employee empSend, Storage revStor ,Storage sendStor) {
        String lastMsgCode = messageRepository.findLastMsgCode();
        String newMsgCode = newMsgCode(lastMsgCode);

        Message message = Message.create(
                newMsgCode,
                LocalDate.now(),
                msgTitle,
                msgCon,
                msgStatus,
                emerStatus
        );

        message.setEmpRev(empRev);
        message.setEmpSend(empSend);
        message.setRevStor(revStor);
        message.setSendStor(sendStor);

        messageRepository.save(message);
    }

    @Transactional
    public void deleteMsg(String msgCode) {

        if(messageRepository.existsById(msgCode)) {
            messageRepository.deleteById(msgCode);
        } else {
            throw new IllegalArgumentException("msgCode가 없음 : " + msgCode);
        }
    }


}
