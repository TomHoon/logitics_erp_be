package com.logitics.erp.employeeeventsupport.controller;

import com.logitics.erp.employeeappointment.service.EmployeeAppointmentService;
import com.logitics.erp.employeeeventsupport.dto.EmployeeEventSupportRegisterRequest;
import com.logitics.erp.employeeeventsupport.dto.EmployeeEventSupportResponse;
import com.logitics.erp.employeeeventsupport.service.EmployeeEventSupportService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/support")
public class EmployeeEventSupportController {

	private final EmployeeEventSupportService employeeEventSupportService;

	@GetMapping
	@Operation(summary = "경조비 신청 조회")
	public List<EmployeeEventSupportResponse> getEventSupportList(
					@RequestParam int page,
					@RequestParam int size,
					@RequestParam(required = false) String keyword
	) {
		return employeeEventSupportService.getSupportList(page, size, keyword);
	}

	@PostMapping
	@Operation(summary = "경조비 신청", description = "경조비 신청합니다.")
	public boolean registerEventSupport(
			@RequestBody EmployeeEventSupportRegisterRequest registerRequest
	) {
		return employeeEventSupportService.registerEventSupport(registerRequest);
	}

	@DeleteMapping("/{eventSupportId}")
	@Operation(summary = "경조비 신청", description = "경조비 신청합니다.")
	public boolean deleteEventSupport(@PathVariable Long eventSupportId) {
		return employeeEventSupportService.deleteEventSupport(eventSupportId);
	}

}
