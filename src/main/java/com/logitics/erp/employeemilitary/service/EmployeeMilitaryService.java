package com.logitics.erp.employeemilitary.service;

import com.logitics.erp.employeemilitary.mapper.EmployeeMilitaryMapper;
import com.logitics.erp.employeemilitary.dto.EmployeeMilitaryInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeMilitaryService {

	private final EmployeeMilitaryMapper employeeMilitaryMapper;

	public List<EmployeeMilitaryInfoResponse> getEmployeeMilitaryInfo(String employeeNo) {
		return employeeMilitaryMapper.getEmployeeMilitaryInfo(employeeNo);
	}

}
