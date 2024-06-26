package synergyhubback.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synergyhubback.post.domain.entity.PostEntity;
import synergyhubback.post.domain.type.PostCommSet;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private String PostCode;
    private String PostName;
    private int PostViewCnt;
    private LocalDate PostDate;
    private String PostCon;
    private PostCommSet postCommSet;
    private char FixStatus;
    private char NoticeStatus;

    public PostResponse(PostEntity postEntity) {
        this.PostCode = postEntity.getPostCode();
        this.PostName = postEntity.getPostName();
        this.PostViewCnt = postEntity.getPostViewCnt();
        this.PostDate = postEntity.getPostDate();
        this.PostCon = postEntity.getPostCon();
        this.postCommSet = postEntity.getPostCommSet();
        this.FixStatus = postEntity.getFixStatus();
        this.NoticeStatus = postEntity.getNoticeStatus();
    }
}
