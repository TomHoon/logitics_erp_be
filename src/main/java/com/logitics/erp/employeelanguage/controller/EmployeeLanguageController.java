package com.logitics.erp.employeelanguage.controller;

import com.logitics.erp.employeelanguage.dto.EmployeeLanguageInfoResponse;
import com.logitics.erp.employeelanguage.service.EmployeeLanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employeeLanguage")
public class EmployeeLanguageController {

	private final EmployeeLanguageService employeeLanguageService;

	@GetMapping
	public List<EmployeeLanguageInfoResponse> getEmployeeLanguageInfo(@RequestParam String employNo) {
		return employeeLanguageService.getEmployeeLanguageInfo(employNo);
	}

}
