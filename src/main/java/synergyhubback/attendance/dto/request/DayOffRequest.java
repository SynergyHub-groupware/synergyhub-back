package synergyhubback.attendance.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import synergyhubback.attendance.domain.entity.DayOffBalance;

@Getter
@Setter
public class DayOffRequest {

    private int doCode;              //휴가코드(pk)
    private String doName;           //휴가명
    private int doUsed;              //신청일수
    private String doStartDate;      //시작일자
    private String doEndDate;        //종료일자
    private String doStartTime;      //시작시간
    private String doEndTime;        //종료시간
    private DayOffBalance dayOffBalance;

    @JsonCreator
    public DayOffRequest(@JsonProperty("doName") String doName,
                         @JsonProperty("doName") int doUsed,
                         @JsonProperty("doStartDate") String doStartDate,
                         @JsonProperty("doEndDate") String doEndDate,
                         @JsonProperty("doStartTime") String doStartTime,
                         @JsonProperty("doEndTime") String doEndTime,
                         @JsonProperty("dayOffBalance") DayOffBalance dayOffBalance) {
        this.doName = doName;
        this.doUsed = doUsed;
        this.doStartDate = doStartDate;
        this.doEndDate = doEndDate;
        this.doStartTime = doStartTime;
        this.doEndTime = doEndTime;
        this.dayOffBalance = dayOffBalance;
    }

}
