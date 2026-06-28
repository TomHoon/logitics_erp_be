package com.logitics.erp.attendance.controller;

import com.logitics.erp.attendance.dto.*;
import com.logitics.erp.attendance.entity.Attendance;
import com.logitics.erp.attendance.service.AttendanceService;
import com.logitics.erp.employee.repository.EmployeeRepository;
import com.logitics.erp.leaverequest.entity.LeaveRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendances")
public class AttendanceController {

	private final AttendanceService attendanceService;
	private final EmployeeRepository employeeRepository;

	@PostMapping("/checkin")
	@Operation(summary = "출근등록")
	public AttendResponse checkin(@RequestBody AttendRequest attendRequest, Authentication authentication) {
		String email = authentication.getName();
		String employeeNo = employeeRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("찾는 유저 정보가 없습니다.")).getEmployeeNo();
		attendRequest.setEmployeeNo(employeeNo);
		return attendanceService.attend(attendRequest);
	}

	@PostMapping("/checkout")
	@Operation(summary = "퇴근등록")
	public AttendResponse checkout(@RequestBody AttendRequest attendRequest, Authentication authentication) {
		String email = authentication.getName();
		String employeeNo = employeeRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("찾는 유저 정보가 없습니다.")).getEmployeeNo();
		attendRequest.setEmployeeNo(employeeNo);
		return attendanceService.checkout(attendRequest);
	}

	@PostMapping("/early-leave")
	@Operation(summary = "조퇴")
	public Map<String, String> earlyLeave(@RequestBody EarlyLeaveRequest request, Authentication authentication) {
		String email = authentication.getName();
		String employeeNo = employeeRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("찾는 유저 정보가 없습니다.")).getEmployeeNo();
		request.setEmployeeNo(employeeNo);
		return attendanceService.earlyLeave(request);
	}

	@PostMapping("/leave")
	@Operation(summary = "연차")
	public Map<String, String> requestLeave(@RequestBody LeaveRequestDTO request, Authentication authentication) {

		String leaveType = request.getLeaveType();

		if (!leaveType.equals("종일")) {
			throw new IllegalArgumentException("현재는 종일 연차만 가능합니다.");
		}

		String email = authentication.getName();
		String employeeNo = employeeRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("찾는 유저 정보가 없습니다.")).getEmployeeNo();
		request.setEmployeeNo(employeeNo);
		return attendanceService.requestLeave(request);
	}

	@GetMapping("/daily")
	@Operation(summary = "일일근태리스트조회")
	public List<AttendanceDailyResponse> getAttendanceDaily(@RequestParam(required = false) String findDate) {
		if (findDate == null) {
			findDate = LocalDate.now().toString();
		}
		List<AttendanceDailyResponse> list = attendanceService.getAttendanceDaily(findDate);
		return list;
	}

	@GetMapping("/monthly")
	@Operation(summary = "월근태현황조회")
	public List<AttendanceResultResponse> getMonthly(@RequestParam(required = false) LocalDate findDate) {
		return attendanceService.getMonthly(findDate);
	}

//	@GetMapping("/late/checkin")
//	@Operation(summary = "월근태현황조회")
//	public List<AttendanceResultResponse> getMonthly(@RequestParam(required = false) String findDate) {
//		return attendanceService.getMonthly(findDate);
//	}

	@GetMapping("/month")
	@Operation(summary = "월근태현황조회(미사용)")
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
