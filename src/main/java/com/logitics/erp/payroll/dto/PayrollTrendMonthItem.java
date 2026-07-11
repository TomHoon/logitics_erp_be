package com.logitics.erp.payroll.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "월별 급여 추이 항목")
public class PayrollTrendMonthItem {

    @Schema(description = "년월 (yyyyMM, 매퍼 조회용)")
    private int payrollYearMonth;

    @Schema(description = "월 (1~12)")
    private int month;

    @Schema(description = "급여명세고유번호")
    private Long payrollId;

    @Schema(description = "급여 등록 여부")
    private boolean hasData;

    @Schema(description = "기본급")
    private int basicSalaryAmount;

    @Schema(description = "수당합계")
    private int totalAllowanceAmount;

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

    @Schema(description = "공제합계")
    private int totalDeductionAmount;

    @Schema(description = "실지급액")
    private int realPayAmount;

    @Schema(description = "지급일")
    private LocalDate paymentDate;

    @Schema(description = "급여상태코드")
    private PayrollStatusCode payrollStatusCode;

    @Schema(description = "급여상태명")
    private String payrollStatusText;
}
