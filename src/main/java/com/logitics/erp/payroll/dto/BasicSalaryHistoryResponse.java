package com.logitics.erp.payroll.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "기본급 변경 이력 항목")
public class BasicSalaryHistoryResponse {

    @Schema(description = "변경일시")
    private LocalDateTime changedAt;

    @Schema(description = "변경 전 금액")
    private int oldAmount;

    @Schema(description = "변경 후 금액")
    private int newAmount;
}
