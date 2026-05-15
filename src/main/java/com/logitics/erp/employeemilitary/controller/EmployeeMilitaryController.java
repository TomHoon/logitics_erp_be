package com.logitics.erp.employeemilitary.controller;

import com.logitics.erp.employeemilitary.dto.EmployeeMilitaryInfoResponse;
import com.logitics.erp.employeemilitary.service.EmployeeMilitaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/military")
public class EmployeeMilitaryController {

	private final EmployeeMilitaryService employeeMilitaryService;

	@GetMapping
	public List<EmployeeMilitaryInfoResponse> getEmployeeMilitaryInfo(@RequestParam String employeeNo) {
		return employeeMilitaryService.getEmployeeMilitaryInfo(employeeNo);
	}

}
