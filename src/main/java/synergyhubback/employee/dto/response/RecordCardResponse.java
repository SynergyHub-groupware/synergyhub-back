package synergyhubback.employee.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import synergyhubback.employee.domain.entity.Certificate;
import synergyhubback.employee.domain.entity.Employee;
import synergyhubback.employee.domain.entity.SchoolInfo;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class RecordCardResponse {

//    private String emp_img;
    private String emp_name;
    private String social_security_no;
    private String phone;
    private String email;
    private String address;

    private List<SchoolInfo> schoolInfos;

    private List<Certificate> certificates;

    public static RecordCardResponse getRecordCard(Employee employee, List<SchoolInfo> schoolInfos, List<Certificate> certificates) {

        return new RecordCardResponse(
                employee.getEmp_name(),
                employee.getSocial_security_no(),
                employee.getPhone(),
                employee.getEmail(),
                employee.getAddress(),
                schoolInfos,
                certificates
        );
    }
}
