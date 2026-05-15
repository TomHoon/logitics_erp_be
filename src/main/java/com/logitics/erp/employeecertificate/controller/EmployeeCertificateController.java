package com.logitics.erp.employeecertificate.controller;

import com.logitics.erp.employeecertificate.dto.EmployeeCertificateInfoResponse;
import com.logitics.erp.employeecertificate.service.EmployeeCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/certificate")
public class EmployeeCertificateController {

	private final EmployeeCertificateService employeeCertificateService;

	@GetMapping
	public List<EmployeeCertificateInfoResponse> getEmployeeCertificateInfo(@RequestParam String employeeNo) {
		return employeeCertificateService.getEmployeeCertificateInfo(employeeNo);
	}

}
