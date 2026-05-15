package com.logitics.erp.employeecertificate.service;

import com.logitics.erp.employeecertificate.dto.EmployeeCertificateInfoResponse;
import com.logitics.erp.employeecertificate.mapper.EmployeeCertificateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeCertificateService {

	private final EmployeeCertificateMapper employeeCertificateMapper;

	public List<EmployeeCertificateInfoResponse> getEmployeeCertificateInfo(String employeeNo) {
		return employeeCertificateMapper.getEmployeeCertificateInfo(employeeNo);
	}

}
