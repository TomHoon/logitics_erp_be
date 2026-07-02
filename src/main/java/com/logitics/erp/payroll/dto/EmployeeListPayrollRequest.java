package com.logitics.erp.payroll.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeListPayrollRequest {

    @Schema(description = "사원명")
    private String name;

    @Schema(description = "부서명")
    private String departmentName;
}
