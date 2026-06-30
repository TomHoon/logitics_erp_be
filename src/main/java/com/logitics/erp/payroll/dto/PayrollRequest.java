package com.logitics.erp.payroll.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollRequest {

    @Schema(description = "부서명")
    private String departmentName;

    @Schema(description = "직급명")
    private String positionName;

    @Schema(description = "검색어")
    private String keyword;

    @Schema(description = "적용기준일")
    private LocalDate applyDate;

}
