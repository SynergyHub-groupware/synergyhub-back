package synergyhubback.message.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synergyhubback.auth.util.TokenUtils;
import synergyhubback.message.domain.entity.Message;
import synergyhubback.message.dto.request.*;
import synergyhubback.message.dto.response.*;
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

    /* 보낸 쪽지 휴지통 업데이트 (PATCH) */
    @PutMapping("/send/{msgCode}/bin")
    public ResponseEntity<Void> sendMsgDel(@PathVariable String msgCode, @RequestBody SendMsgDelRequest request) {

        messageService.SendMsgDel(msgCode, request.getStorCode());

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

    /* 업무 보관함 전체 조회 */
    @GetMapping("/work")
    public ResponseEntity<List<WorkResponse>> getWorkMessage(@RequestHeader("Authorization") String token) {

        String jwtToken = TokenUtils.getToken(token);
        String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
        int empCode = Integer.parseInt(tokenEmpCode);

        List<WorkResponse> workList = messageService.getWorkMessage(empCode);

        System.out.println("workList : controller : " + workList);

        return new ResponseEntity<>(workList, HttpStatus.OK);
    }

    /* 임시 저장 전체 조회 */
    @GetMapping("/temp")
    public ResponseEntity<List<TempResponse>> getTempMessage(@RequestHeader("Authorization") String token) {

        String jwtToken = TokenUtils.getToken(token);
        String tokenEmpCode = TokenUtils.getEmp_Code(jwtToken);
        int empCode = Integer.parseInt(tokenEmpCode);

        List<TempResponse> tempList = messageService.getTempMessage(empCode);

        System.out.println("tempList = " + tempList);

        return new ResponseEntity<>(tempList, HttpStatus.OK);
    }

    /* MS 코드에 따라 Rev Detail 조회 */
    @GetMapping("/receive/{msgCode}")
    public ResponseEntity<ReceiveResponse> getMsgByCode(@PathVariable String msgCode) {

        Message message = messageService.findMsgByMsgCode(msgCode);
        if(message == null) {
            return ResponseEntity.notFound().build();
        }

        ReceiveResponse response = ReceiveResponse.getReceiveMessage(message);
        return ResponseEntity.ok(response);
    }

    /* MS 코드에 따라 Send Detail 조회 */
    @GetMapping("/send/{msgCode}")
    public ResponseEntity<SendResponse> getSendMsgByCode(@PathVariable String msgCode) {

        Message message = messageService.findSendMsgByMsgCode(msgCode);
        if(message == null) {
            return ResponseEntity.notFound().build();
        }

        SendResponse response = SendResponse.getSendMessage(message);
        return ResponseEntity.ok(response);
    }

    /* Message Send (Insert) */
    @PostMapping("/send")
    public ResponseEntity<ResponseMsg> createMessage(@RequestBody CreateMsgRequest request) {

        try {
            messageService.createMessage(
                    request.getMsgTitle(),
                    request.getMsgCon(),
                    request.getMsgStatus(),
                    request.getEmerStatus(),
                    request.getEmpRev(),
                    request.getEmpSend(),
                    request.getRevStor(),
                    request.getSendStor());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseMsg(200, "메세지를 전송했습니다.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMsg(500, "서버 오류" + e.getMessage(), null));
        }
    }

    /* Temp Message Create (Insert) */
    @PostMapping("/create/temp")
    public ResponseEntity<ResponseMsg> createTemp(@RequestBody CreateTempRequest request) {

        try {
            messageService.createTemp(
                    request.getMsgTitle(),
                    request.getMsgCon(),
                    request.getMsgStatus(),
                    request.getEmerStatus(),
                    request.getEmpRev(),
                    request.getEmpSend(),
                    request.getRevStor(),
                    request.getSendStor()
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseMsg(200, "임시저장 되었습니다.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMsg(500, "서버 오류" + e.getMessage(), null));
        }
    }

    /* 쪽지 완전 삭제 */
    @DeleteMapping("/bin/{msgCode}")
    public ResponseEntity<Void> deleteMsg(@PathVariable String msgCode){

        try {
            messageService.deleteMsg(msgCode);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /* 쪽지 읽음 처리 */
    @PatchMapping("/{msgCode}/read")
    public ResponseEntity<Void> changeStatusByReadMsg(@PathVariable String msgCode) {

        messageService.changeStatusByReadMsg(msgCode);

        return ResponseEntity.noContent().build();
    }
}
