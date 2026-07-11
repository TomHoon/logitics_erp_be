package com.logitics.erp.payroll.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "개인 급여조회(연간 월별 추이) 응답")
public class PayrollTrendResponse {

    @Schema(description = "사원번호")
    private String employeeNo;

    @Schema(description = "성명")
    private String employeeName;

    @Schema(description = "부서명")
    private String departmentName;

    @Schema(description = "직급명")
    private String positionName;

    @Schema(description = "조회년도")
    private int year;

    @Schema(description = "1~12월 급여 목록")
    private List<PayrollTrendMonthItem> monthlyList;
}
