package synergyhubback.attendance.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DayOffRequest {

    private int doCode;                //휴가코드(pk)
    private String doName;              //휴가명
    private String doStartDate;      //시작일자
    private String doEndDate;        //종료일자
    private String doStartTime;      //시작시간
    private String doEndTime;        //종료시간
    private String doInsertDate;     //부여일자
    private int granted;               //부여수
    private int used;                  //사용수
    private int remaining;             //잔여수

    @JsonCreator
    public DayOffRequest(@JsonProperty("doName") String doName,
                         @JsonProperty("doStartDate") String doStartDate,
                         @JsonProperty("doEndDate") String doEndDate,
                         @JsonProperty("doStartTime") String doStartTime,
                         @JsonProperty("doEndTime") String doEndTime,
                         @JsonProperty("doInsertDate") String doInsertDate,
                         @JsonProperty("granted") int granted,
                         @JsonProperty("used") int used,
                         @JsonProperty("remaining") int remaining
                         ) {
        this.doName = doName;
        this.doStartDate = doStartDate;
        this.doEndDate = doEndDate;
        this.doStartTime = doStartTime;
        this.doEndTime = doEndTime;
        this.doInsertDate = doInsertDate;
        this.granted = granted;
        this.used = used;
        this.remaining = remaining;
    }

}
