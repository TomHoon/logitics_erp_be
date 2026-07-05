package com.logitics.erp.payroll.dto;

import com.logitics.erp.payroll.entity.Payroll;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "급여지급 정보")
public class PayrollResponse {

    @Schema(description = "급여명세고유번호", example = "5001")
    private String payrollId;
    
    @Schema(description = "사원번호", example = "T9950")
    private String employeeNo;

    @Schema(description = "성명", example = "홍길동")
    private String employeeName;

    @Schema(description = "부서명", example = "인사팀")
    private String departmentName;

    @Schema(description = "직급명", example = "과장")
    private String positionName;

    @Schema(description = "기본급", example = "3000000")
    private String basicSalaryAmount;

    @Schema(description = "식대", example = "200000")
    private String mealAllowanceAmount;

    @Schema(description = "교통비", example = "100000")
    private String transportationAllowanceAmount;

    @Schema(description = "직책수당", example = "300000")
    private String responsibilityAllowanceAmount;

    @Schema(description = "직급수당", example = "300000")
    private String positionAllowanceAmount;

    @Schema(description = "수당 합계", example = "600000")
    private String totalAllowanceAmount;

    @Schema(description = "은행명", example = "국민은행")
    private String bankName;

    @Schema(description = "계좌번호", example = "123-456-789012")
    private String accountNumber;

    public PayrollResponse(Payroll p) {
        this.employeeNo = p.getEmployee().getEmployeeNo();
        this.employeeName = p.getEmployee().getName();
        this.departmentName = p.getDepartmentNameSnapshot();
        this.positionName = p.getPositionNameSnapshot();
    }
}
