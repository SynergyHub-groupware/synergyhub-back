package synergyhubback.approval.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import synergyhubback.common.attachment.AttachmentEntity;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ApprovalAttachRequest {

    @NotBlank
    private List<AttachmentEntity> attachmentList;
}
