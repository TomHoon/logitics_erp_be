package com.logitics.erp.employeelanguage.service;

import com.logitics.erp.employeelanguage.dto.EmployeeLanguageInfoResponse;
import com.logitics.erp.employeelanguage.mapper.EmployeeLanguageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeLanguageService {
	private final EmployeeLanguageMapper employeeLanguageMapper;

	public List<EmployeeLanguageInfoResponse> getEmployeeLanguageInfo(String employeeNo) {
		return employeeLanguageMapper.getEmployeeLanguageInfo(employeeNo);
	}
}
