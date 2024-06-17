package synergyhubback.post.dto.request;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import synergyhubback.post.domain.entity.LowBoardEntity;
import synergyhubback.post.domain.entity.PostSortEntity;
import synergyhubback.post.domain.type.PostCommSet;

import java.time.LocalDate;
import java.util.Date;

@Getter
@RequiredArgsConstructor
@Setter
public class PostRequest {
    private String PostCode;
    private String PostName;
    private int PostViewCnt;
    private LocalDate PostDate;
    private String PostCon;
    private PostCommSet postCommSet;
    private char FixStatus;
    private char NoticeStatus;


    private int EmpCode;
    private LowBoardEntity LowBoardCode;
    private PostSortEntity PsCode;

}
