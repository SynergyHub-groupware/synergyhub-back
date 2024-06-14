package synergyhubback.employee.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import synergyhubback.employee.domain.entity.Department;
import synergyhubback.employee.domain.entity.DeptRelations;
import synergyhubback.employee.domain.repository.DepartmentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class DepartmentResponse {

    private List<DeptDetailResponse> departmentList;

    public static DepartmentResponse from(List<DeptDetailResponse> departmentList) {
        return new DepartmentResponse(departmentList);
    }

}
