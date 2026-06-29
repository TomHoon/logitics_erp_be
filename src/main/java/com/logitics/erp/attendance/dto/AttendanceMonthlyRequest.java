package com.logitics.erp.attendance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceMonthlyRequest {
    @Schema(description = "찾는 날짜", example = "2026-06-28")
    private LocalDate findDate;

    @Schema(description = "찾는 부서", example = "IT본부")
    private String departmentName;
}
