package synergyhubback.common.attachment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "attachment")
@Getter
@Setter
public class AttachmentEntity {

    @Id
    @Column(name = "attach_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attachCode;
    @Column(name = "attach_original")
    private String attachOriginal;
    @Column(name = "attach_save")
    private String attachSave;
    @Column(name = "attach_url")
    private String attachUrl;
    @Column(name = "attach_sort")
    private String attachSort;


}
