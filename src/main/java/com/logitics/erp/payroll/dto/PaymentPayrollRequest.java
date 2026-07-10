package com.logitics.erp.payroll.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentPayrollRequest {

    @Schema(description = "부서명")
    private String departmentName;

    @Schema(description = "검색어(사원명)")
    private String keyword;

    @Schema(description = "조회 기준일 (해당 년월 조회)")
    private LocalDate applyDate;
}
