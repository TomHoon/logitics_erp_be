package com.logitics.erp.attendance.controller;

import com.logitics.erp.attendance.dto.*;
import com.logitics.erp.attendance.entity.Attendance;
import com.logitics.erp.attendance.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendances")
public class AttendanceController {

	private final AttendanceService attendanceService;

	@PostMapping("/attend")
	@Operation(summary = "출근등록")
	public AttendResponse attend(@RequestBody @Valid AttendRequest attendRequest) {
		return attendanceService.attend(attendRequest);
	}

	@GetMapping("/daily")
	@Operation(summary = "일일근태리스트조회")
	public List<AttendanceDailyResponse> getAttendanceDaily() {
		List<AttendanceDailyResponse> list = attendanceService.getAttendanceDaily();
		return list;
	}

	@GetMapping("/month")
	@Operation(summary = "월근태현황조회")
	public List<AttendanceResultResponse> getMonthAttendance(
					@RequestParam(defaultValue = "10") int size,
					@RequestParam(defaultValue = "0") int page,
					@RequestParam(required = false) Long departmentId,
					@RequestParam(required = false) String startDate
	) {
		if (startDate.isEmpty()) {
			startDate = LocalDate.now().toString();
		}

		return attendanceService.getMonthAttendance(size, page, departmentId, startDate);
	}
}
