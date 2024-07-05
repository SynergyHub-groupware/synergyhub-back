package synergyhubback.employee.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synergyhubback.employee.domain.entity.Employee;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeResponse {

        private String position_code;
        private int emp_code;
        private Integer par_code;
        private String emp_name;
        private String dept_code;
        private String dept_title;
        private String position_name;
        private String title_name;
        private String phone;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate hire_date;
        private String emp_status;
        private String social_security_no;              //생년월일로 포맷함
        private String emp_img;
        private List<EmployeeResponse> children = new ArrayList<>();        // 자식 필드 추가

        public static EmployeeResponse fromEntity(Employee employee) {

                return new EmployeeResponse(

                        employee.getPosition().getPosition_code(),
                        employee.getEmp_code(),
                        employee.getPar_code(),
                        employee.getEmp_name(),
                        employee.getDepartment().getDept_code(),
                        employee.getDepartment().getDept_title(),
                        employee.getPosition().getPosition_name(),
                        employee.getTitle().getTitle_name(),
                        employee.getPhone(),
                        employee.getHire_date(),
                        employee.getEmp_status(),
                        employee.getSocial_security_no(),
                        employee.getEmp_img(),
                        new ArrayList<>()       // 자식 리스트 초기화
                );
        }

        public String formatSocialSecurityNo() {
                if (social_security_no != null) {
                        String cleanedSSN = social_security_no.replaceAll("[^0-9]", "");

                        if (cleanedSSN.length() >= 6) {
                                String datePart = cleanedSSN.substring(0, 6);

                                try {
                                        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyMMdd");
                                        LocalDate date = LocalDate.parse(datePart, inputFormatter);

                                        int yearPart = Integer.parseInt(cleanedSSN.substring(0, 2));
                                        int century = determineCentury(yearPart);

                                        date = date.withYear(century + yearPart);

                                        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                        return date.format(outputFormatter);

                                } catch (DateTimeParseException | NumberFormatException e) {
                                        e.printStackTrace();
                                }
                        }
                }
                return social_security_no;
        }
        private int determineCentury(int yearPart) {
                if (yearPart >= 00 && yearPart <= 24) {
                        return 2000;
                } else {
                        return 1900;
                }
        }

        @JsonGetter("social_security_no")
        public String getFormattedSocialSecurityNo() {
                return formatSocialSecurityNo();
        }

        public void setDept_title(String dept_title) {
                this.dept_title = dept_title;
        }

        public void setPosition_name(String position_name) {
                this.position_name = position_name;
        }

        public void setChildren(List<EmployeeResponse> children) {
                this.children = children;
        }
}

