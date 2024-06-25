package synergyhubback.message.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synergyhubback.auth.util.TokenUtils;
import synergyhubback.message.domain.entity.Message;
import synergyhubback.message.dto.request.RevMsgDelRequest;
import synergyhubback.message.dto.response.BinResponse;
import synergyhubback.message.dto.response.ImpResponse;
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

    /* 휴지통 전체 리스트 조회 */
    @GetMapping("/bin")
    public ResponseEntity<List<BinResponse>> getBinMessage (@RequestHeader("Authorization") String token) {

        String jwtToken = TokenUtils.getToken(token);
        String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
        int empCode = Integer.parseInt(tokenEmpCode);

        List<BinResponse> binList = messageService.getBinMessage(empCode);

        System.out.println("binList : controller : " + binList.size());

        return new ResponseEntity<>(binList, HttpStatus.OK);
    }

    /* 받은 쪽지 휴지통 업데이트(PATCH) */
    @PutMapping("/receive/{msgCode}/bin")
    public ResponseEntity<Void> RevMsgDel(@PathVariable String msgCode, @RequestBody RevMsgDelRequest request) {
        messageService.RevMsgDel(msgCode, request.getStorCode());

        return ResponseEntity.noContent().build();
    }

    /* 중요 보관함 전체 조회 */
    @GetMapping("/important")
    public ResponseEntity<List<ImpResponse>> getImpMessage (@RequestHeader("Authorization") String token) {

        String jwtToken = TokenUtils.getToken(token);
        String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
        int empCode = Integer.parseInt(tokenEmpCode);

        List<ImpResponse> impList = messageService.getImpMessage(empCode);

        System.out.println("impList : controller : " + impList.size());

        return new ResponseEntity<>(impList, HttpStatus.OK);
    }
}
