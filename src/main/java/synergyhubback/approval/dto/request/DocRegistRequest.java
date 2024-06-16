package synergyhubback.approval.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import synergyhubback.approval.domain.entity.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class DocRegistRequest {
    @NotBlank
    private String adTitle;
    @NotNull
    private int empCode;
    @NotNull
    private LocalDate adReportDate;
    @NotBlank
    private String adStatus;

    private Form form;
    private Etc etc;
    private Personal personal;
    private ApprovalAttendance approvalAttendance;

    private ApprovalAppoint approvalAppoint;
    private List<AppointDetail> appointDetailList;
}
