package synergyhubback.post.dto.request;

import synergyhubback.post.domain.entity.LowBoardEntity;

public class PostRollRequest {
    private char prWriteRole;
    private LowBoardEntity lowCode;
    private int emp_Code; // assuming emp_code is an int
    private char prAdmin;
    private String emp_Name;
    private String dept_title;
    private String position_name;

    public PostRollRequest(char prWriteRole, LowBoardEntity lowCode, int empCode, char prAdmin, String empName, String deptTitle, String positionName) {
        this.prWriteRole = prWriteRole;
        this.lowCode = lowCode;
        this.emp_Code = empCode;
        this.prAdmin = prAdmin;
        this.emp_Name = empName;
        this.dept_title = deptTitle;
        this.position_name = positionName;
    }

    // Getters and setters (generated or manually implemented)
    public char getPrWriteRole() {
        return prWriteRole;
    }

    public void setPrWriteRole(char prWriteRole) {
        this.prWriteRole = prWriteRole;
    }

    public LowBoardEntity getLowCode() {
        return lowCode;
    }

    public void setLowCode(LowBoardEntity lowCode) {
        this.lowCode = lowCode;
    }

    public int getEmpCode() {
        return emp_Code;
    }

    public void setEmpCode(int empCode) {
        this.emp_Code = empCode;
    }

    public char getPrAdmin() {
        return prAdmin;
    }

    public void setPrAdmin(char prAdmin) {
        this.prAdmin = prAdmin;
    }

    public String getEmpName() {
        return emp_Name;
    }

    public void setEmpName(String empName) {
        this.emp_Name = empName;
    }

    public String getDeptTitle() {
        return dept_title;
    }

    public void setDeptTitle(String deptTitle) {
        this.dept_title = deptTitle;
    }

    public String getPositionName() {
        return position_name;
    }

    public void setPositionName(String positionName) {
        this.position_name = positionName;
    }
}
