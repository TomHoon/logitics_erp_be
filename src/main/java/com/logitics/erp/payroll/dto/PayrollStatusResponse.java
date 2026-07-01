package com.logitics.erp.payroll.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollStatusResponse {
    @Schema(description = "평균 기본급")
    private Long averageBasicSalary;

    @Schema(description = "전월 대비 증감액")
    private Long compareLastMonthAmount;

    @Schema(description = "최고 기본급")
    private Long maxBasicSalary;

    @Schema(description = "최고 기본급 사원명")
    private String maxBasicSalaryEmployeeName;

    @Schema(description = "최고 기본급 사원 직급")
    private String maxBasicSalaryEmployeePositionName;

    @Schema(description = "총 기본급")
    private Long totalBasicSalaryAmount;

    @Schema(description = "총 수당")
    private Long totalAllowanceAmount;

    @Schema(description = "총 사원 수")
    private int employeeCount;


}
