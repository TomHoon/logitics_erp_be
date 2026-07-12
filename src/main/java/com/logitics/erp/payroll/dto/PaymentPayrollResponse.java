package com.logitics.erp.payroll.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "급여지급 목록 정보")
public class PaymentPayrollResponse {

    @Schema(description = "급여명세고유번호")
    private Long payrollId;

    @Schema(description = "사원번호")
    private String employeeNo;

    @Schema(description = "성명")
    private String employeeName;

    @Schema(description = "부서명")
    private String departmentName;

    @Schema(description = "기본급")
    private int basicSalaryAmount;

    @Schema(description = "식대")
    private int mealAllowanceAmount;

    @Schema(description = "교통비")
    private int transportationAllowanceAmount;

    @Schema(description = "야근수당")
    private int overtimeAllowanceAmount;

    @Schema(description = "지급소계")
    private int totalPayAmount;

    @Schema(description = "국민연금")
    private int nationalPensionAmount;

    @Schema(description = "건강보험")
    private int healthInsuranceAmount;

    @Schema(description = "고용보험")
    private int employmentInsuranceAmount;

    @Schema(description = "소득세")
    private int incomeTaxAmount;

    @Schema(description = "공제소계")
    private int totalDeductionAmount;

    @Schema(description = "실지급액")
    private int realPayAmount;

    @Schema(description = "지급일")
    private LocalDate paymentDate;

    @Schema(description = "급여상태코드")
    private PayrollStatusCode payrollStatusCode;

    @Schema(description = "급여상태명")
    private String payrollStatusText;

    @Schema(description = "지금 급여확정 가능 여부")
    private boolean confirmable;

    @Schema(description = "확정 불가 사유 (확정 불가능한 경우에만)")
    private String notConfirmableReason;
}
