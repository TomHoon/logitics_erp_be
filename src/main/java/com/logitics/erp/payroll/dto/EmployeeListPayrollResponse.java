package com.logitics.erp.payroll.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeListPayrollResponse {

    @Schema(description = "직급명", example = "대리")
    private String positionName;

    @Schema(description = "부서명", example = "인사팀")
    private String departmentName;

    @Schema(description = "사원명", example = "김철수")
    private String name;

    @Schema(description = "사원번호", example = "T00691")
    private String employeeNo;

    @Schema(description = "입사일", example = "2025-03-01")
    private LocalDate hireDate;

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

}
