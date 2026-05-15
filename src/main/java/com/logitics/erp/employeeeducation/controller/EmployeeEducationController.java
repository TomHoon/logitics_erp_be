package com.logitics.erp.employeeeducation.controller;

import com.logitics.erp.employeeeducation.dto.EmployeeEducationInfoResponse;
import com.logitics.erp.employeeeducation.entity.EmployeeEducation;
import com.logitics.erp.employeeeducation.service.EmployeeEducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/education")
public class EmployeeEducationController {

	private final EmployeeEducationService employeeEducationService;

	@GetMapping
	public List<EmployeeEducationInfoResponse> getEmployeeEducationInfo(String employeeNo) {
		return employeeEducationService.getEmployeeEducationInfo(employeeNo);
	}

}
