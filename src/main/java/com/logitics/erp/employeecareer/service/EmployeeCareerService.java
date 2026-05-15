package com.logitics.erp.employeecareer.service;

import com.logitics.erp.employeecareer.dto.EmployeeCareerAddInfoRequest;
import com.logitics.erp.employeecareer.dto.EmployeeCareerInfoResponse;
import com.logitics.erp.employeecareer.mapper.EmployeeCareerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeCareerService {

	private final EmployeeCareerMapper employeeCareerMapper;

	public List<EmployeeCareerInfoResponse> getEmployeeCareerInfo(String employeeNo) {
		return employeeCareerMapper.getEmployeeCareerInfo(employeeNo);
	}

	public List<EmployeeCareerInfoResponse> addInfo(EmployeeCareerAddInfoRequest addRequest) {
		return employeeCareerMapper.addInfo(addRequest);
	}
}
