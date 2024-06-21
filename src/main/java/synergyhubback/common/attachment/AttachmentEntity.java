package synergyhubback.common.attachment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ATTACHMENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AttachmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attachCode;
    private String attachOriginal;
    private String attachSave;
    private String attachUrl;
    private String attachSort;

    private AttachmentEntity(String attachOriginal, String attachSave, String attachUrl, String attachSort){
        this.attachOriginal = attachOriginal;
        this.attachSave = attachSave;
        this.attachUrl = attachUrl;
        this.attachSort = attachSort;
    }
}

    public static AttachmentEntity of(String attachOriginal, String attachSave, String attachUrl, String attachSort){
        return new AttachmentEntity(attachOriginal, attachSave, attachUrl, attachSort);
    }
}
