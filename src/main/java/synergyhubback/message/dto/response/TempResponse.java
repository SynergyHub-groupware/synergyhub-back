package synergyhubback.message.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import synergyhubback.message.domain.entity.Message;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TempResponse {

    private String msgCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate sendDate;
    private String msgTitle;
    private String msgCon;
    private String msgStatus;
    private String emerStatus;
    private int empRev;
    private int empSend;
    private int storCode;
    private String revName;
    private String revPosition;

    public static TempResponse getTempMessage(Message message) {

        return new TempResponse(
                message.getMsgCode(),
                message.getSendDate(),
                message.getMsgTitle(),
                message.getMsgCon(),
                message.getMsgStatus(),
                message.getEmerStatus(),
                message.getEmpRev().getEmp_code(),
                message.getEmpSend().getEmp_code(),
                message.getStorCode().getStorCode(),
                message.getEmpRev().getEmp_name(),
                message.getEmpRev().getPosition().getPosition_name()
        );
    }

}
