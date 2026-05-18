package com.logitics.erp.employeeeventsupport.service;

import com.logitics.erp.employeeeventsupport.dto.EmployeeEventSupportRegisterRequest;
import com.logitics.erp.employeeeventsupport.dto.EmployeeEventSupportResponse;
import com.logitics.erp.employeeeventsupport.mapper.EmployeeEventSupportMapper;
import com.logitics.erp.employeeeventsupport.repository.EmployeeEventSupportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeEventSupportService {

	private final EmployeeEventSupportMapper employeeEventSupportMapper;
	private final EmployeeEventSupportRepository employeeEventSupportRepository;

	public List<EmployeeEventSupportResponse> getSupportList(int page, int size, String keyword) {
		int offset = page * size;
		return employeeEventSupportMapper.getSupportList(size, offset, keyword);
	}

	public boolean registerEventSupport(EmployeeEventSupportRegisterRequest registerRequest) {
		return employeeEventSupportMapper.registerEventSupport(registerRequest) > 0;
	}

	public boolean deleteEventSupport(Long eventSupportId) {
		return employeeEventSupportMapper.deleteEventSupport(eventSupportId) > 0;
	}
}
