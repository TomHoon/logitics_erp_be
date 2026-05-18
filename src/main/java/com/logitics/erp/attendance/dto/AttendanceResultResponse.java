package com.logitics.erp.attendance.dto;

import com.logitics.erp.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AttendanceResultResponse {
	private Long attendanceId;
	private Long employeeId;
	private LocalDate workDate;
	private LocalDateTime checkInTime;
	private LocalDateTime checkOutTime;

	private Integer workMinutes;
	private Integer overtimeMinutes;
	private Integer nightWorkMinutes;
	private Integer lateMinutes;
	private Integer earlyLeaveMinutes;

	private String attendanceStatusCode;
}
