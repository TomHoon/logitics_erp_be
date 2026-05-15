package com.logitics.erp.attendance.controller;

import com.logitics.erp.attendance.dto.AttendRequest;
import com.logitics.erp.attendance.dto.AttendResponse;
import com.logitics.erp.attendance.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendances")
public class AttendanceController {

	private final AttendanceService attendanceService;

	@PostMapping("/attend")
	public AttendResponse attend(@RequestBody @Valid AttendRequest attendRequest) {
		return attendanceService.attend(attendRequest);
	}
}
