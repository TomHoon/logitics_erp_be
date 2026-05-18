package com.logitics.erp.attendance.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AttendRequest {
	private String employeeNo;
	private LocalDate workDate;
	private LocalDateTime checkInTime;
	private Integer workMinutes;
	private String attendanceStatusCode;
	private String memo;

}
