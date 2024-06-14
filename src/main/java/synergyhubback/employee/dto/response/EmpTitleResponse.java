package synergyhubback.employee.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import synergyhubback.employee.domain.entity.Position;
import synergyhubback.employee.domain.entity.Title;

@Getter
@Setter
@AllArgsConstructor
public class EmpTitleResponse {

    private String title_code;

    private String title_name;

    private String emp_name;


    public static EmpTitleResponse from(Title title, String emp_name) {

        return new EmpTitleResponse(

                title.getTitle_code(),
                title.getTitle_name(),
                emp_name

        );
    }
}
