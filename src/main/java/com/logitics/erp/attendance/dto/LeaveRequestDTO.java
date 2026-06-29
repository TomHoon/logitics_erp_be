package com.logitics.erp.attendance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LeaveRequestDTO {

    @Schema(description = "사원번호(파라미터 포함 안함)", example = "T00001")
    private String employeeNo;

    @Schema(description = "연차시작", example = "2026-06-5")
    private LocalDate startDate;

    @Schema(description = "연차종료", example = "2026-06-10")
    private LocalDate endDate;

    @Schema(description = "연차구분", example = "연차/오전/오후")
    private String leaveType;

    @Schema(description = "사유", example = "개인사유")
    private String leaveReason;

}

