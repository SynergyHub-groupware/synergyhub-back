package synergyhubback.message.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synergyhubback.auth.util.TokenUtils;
import synergyhubback.message.dto.response.ReceiveResponse;
import synergyhubback.message.dto.response.SendResponse;
import synergyhubback.message.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/emp/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    /* 받은 쪽지 전체 리스트 조회 */
    @GetMapping("/receive")
    public ResponseEntity<List<ReceiveResponse>> getReceiveMessage(@RequestHeader("Authorization") String token) {

        String jwtToken = TokenUtils.getToken(token);
        String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
        int empCode = Integer.parseInt(tokenEmpCode);

        List<ReceiveResponse> receiveList = messageService.getReceiveMessage(empCode);

        System.out.println("receiveList : controller : " + receiveList.size());

        return new ResponseEntity<>(receiveList, HttpStatus.OK);
    }

    /* 보낸 쪽지 전체 리스트 조회 */
    @GetMapping("/send")
    public ResponseEntity<List<SendResponse>> getSendMessage (@RequestHeader("Authorization") String token) {

        String jwtToken = TokenUtils.getToken(token);
        String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
        int empCode = Integer.parseInt(tokenEmpCode);

        List<SendResponse> sendList = messageService.getSendMessage(empCode);

        System.out.println("sendList : controller : " + sendList.size());

        return new ResponseEntity<>(sendList, HttpStatus.OK);
    }

}