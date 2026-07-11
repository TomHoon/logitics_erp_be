package com.logitics.erp.payroll.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "사원별 급여 변경 이력 항목")
public class PayrollHistoryItem {

    @Schema(description = "급여명세고유번호")
    private Long payrollId;

    @Schema(description = "년월 (yyyyMM)")
    private int payrollYearMonth;

    @Schema(description = "기본급")
    private int basicSalaryAmount;

    @Schema(description = "식대")
    private int mealAllowanceAmount;

    @Schema(description = "교통비")
    private int transportationAllowanceAmount;

    @Schema(description = "직급수당")
    private int positionAllowanceAmount;

    @Schema(description = "수당합계")
    private int totalAllowanceAmount;

    @Schema(description = "지급일")
    private LocalDate paymentDate;

    @Schema(description = "급여상태코드")
    private PayrollStatusCode payrollStatusCode;

    @Schema(description = "급여상태명")
    private String payrollStatusText;
}
