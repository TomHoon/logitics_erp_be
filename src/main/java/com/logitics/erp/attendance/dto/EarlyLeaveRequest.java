package com.logitics.erp.attendance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EarlyLeaveRequest {

    @Schema(description = "(조퇴처리시 파라미터 필요없음) 사원번호")
    private String employeeNo;
    
    @Schema(description = "조퇴시간")
    private LocalDateTime earlyLeaveTime;

    @Schema(description = "사유")
    private String reason;

}
