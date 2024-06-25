package synergyhubback.message.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.employee.domain.entity.Employee;
import synergyhubback.employee.domain.repository.EmployeeRepository;
import synergyhubback.message.domain.entity.Message;
import synergyhubback.message.domain.entity.Storage;
import synergyhubback.message.domain.repository.MessageRepository;
import synergyhubback.message.domain.repository.StorageRepository;
import synergyhubback.message.dto.response.BinResponse;
import synergyhubback.message.dto.response.ReceiveResponse;
import synergyhubback.message.dto.response.SendResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service("messageService")
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final StorageRepository storageRepository;

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

    @Transactional
    public void RevMsgDel(String msgCode, int storCode) {
        Message message = messageRepository.findById(msgCode)
                .orElseThrow(() -> new IllegalArgumentException("message not found with msgCode : " + msgCode));

        Storage storage = storageRepository.findById(storCode)
                .orElseThrow(() -> new IllegalArgumentException("storage not found with storCode : " + storCode ));

        message.setStorCode(storage);

        messageRepository.save(message);
    }

}
