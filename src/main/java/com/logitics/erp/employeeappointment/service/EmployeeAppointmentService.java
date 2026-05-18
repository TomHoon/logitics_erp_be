package com.logitics.erp.employeeappointment.service;

import com.logitics.erp.employeeappointment.dto.EmployeementAppointmentResponse;
import com.logitics.erp.employeeappointment.dto.RegisterAppointmentRequest;
import com.logitics.erp.employeeappointment.mapper.EmployeeAppointmentMapper;
import com.logitics.erp.employeeeventsupport.dto.EmployeeEventSupportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeAppointmentService {

	private final EmployeeAppointmentMapper employeeAppointmentMapper;

	public List<EmployeementAppointmentResponse> getEmployeeAppointmentHistory(int page, int size, String keyword) {
		int offset = page * size;
		return employeeAppointmentMapper.getEmployeeAppointmentHistory(size, offset, keyword);
	}

	public boolean registerAppointment(RegisterAppointmentRequest registerAppointmentRequest) {
		return employeeAppointmentMapper.registerAppointment(registerAppointmentRequest) > 0;
	}
}
