package com.logitics.erp.attendance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendBasicInfoResponse {

    @Schema(description = "총 부여 휴가기간")
    private String totalDays;

    @Schema(description = "남은 휴가기간")
    private String remainDays;

    @Schema(description = "사용 휴가기간")
    private String usedDays;

    @Schema(description = "출근 시간(미출근시 데이터 없음)")
    private String checkInTime;

}
