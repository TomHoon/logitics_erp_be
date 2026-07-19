package com.logitics.erp.transport.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoItem {
    private String cargoName;
    private String quantity;
    private String weight;

    @Schema(description = "가로")
    private Long width;

    @Schema(description = "세로")
    private Long depth;

    @Schema(description = "높이")
    private String height;
}
