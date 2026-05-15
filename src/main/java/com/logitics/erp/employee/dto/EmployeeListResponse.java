package com.logitics.erp.employee.dto;

import lombok.Data;

@Data
public class EmployeeListResponse {

	private Long employeeId;
	private String employeeNo;
	private String name;
	private String department;


}
