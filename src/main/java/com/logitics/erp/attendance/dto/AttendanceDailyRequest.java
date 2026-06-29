package com.logitics.erp.attendance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDailyRequest {
    @Schema(description = "찾는 날짜(없는 경우 오늘날짜)")
    private String findDate;

    @Schema(description = "찾는 부서")
    private String departmentName;

    @Schema(description = "찾는 키워드")
    private String keyword;
}
