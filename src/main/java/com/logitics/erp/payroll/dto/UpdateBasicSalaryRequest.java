package com.logitics.erp.payroll.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBasicSalaryRequest {

    @Schema(description = "수정할 기본급 금액")
    private int amount;

}
