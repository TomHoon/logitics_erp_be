package com.logitics.erp.employeecareer.controller;

import com.logitics.erp.employeecareer.dto.EmployeeCareerAddInfoRequest;
import com.logitics.erp.employeecareer.dto.EmployeeCareerInfoResponse;
import com.logitics.erp.employeecareer.service.EmployeeCareerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/career")
public class EmployeeCareerController {

	private final EmployeeCareerService employeeCareerService;

	@GetMapping
	public List<EmployeeCareerInfoResponse> getEmployeeCareerInfo(@RequestParam String employeeNo) {
		return employeeCareerService.getEmployeeCareerInfo(employeeNo);
	}

	@PostMapping("/addInfo")
	@Operation(summary = "행추가", description = "행추가")
	public List<EmployeeCareerInfoResponse> addInfo(@RequestBody EmployeeCareerAddInfoRequest addRequest) {
		return employeeCareerService.addInfo(addRequest);
	}

}
