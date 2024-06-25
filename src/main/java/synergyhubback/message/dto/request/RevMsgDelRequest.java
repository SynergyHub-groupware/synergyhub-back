package synergyhubback.message.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class RevMsgDelRequest {

    private String msgCode;
    private int storCode;

}
