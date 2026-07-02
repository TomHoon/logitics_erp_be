package com.logitics.erp.payroll.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterSalaryRequest {
    @Schema(description = "사원번호", example = "T00691")
    private String employeeNo;

    @Schema(description = "기본급(원)", example = "3200000")
    private Long basicSalary;

    @Schema(description = "식대(원)", example = "200000")
    private Long mealAllowance;

    @Schema(description = "교통비(원)", example = "100000")
    private Long transportationAllowance;

    @Schema(description = "직급수당(원)", example = "300000")
    private Long responsibilityAllowance;

    @Schema(description = "급여 지급일", example = "25일")
    private String paymentDate;

    @Schema(description = "사원고유아이디 (파라미터 포함x)", example = "25일")
    private Long employeeId;

    @Schema(description = "급여명세년월 (파라미터 포함x)", example = "202607")
    private Integer payrollYearMonth;
}
