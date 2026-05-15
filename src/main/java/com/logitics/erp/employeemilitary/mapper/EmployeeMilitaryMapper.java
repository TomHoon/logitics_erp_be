package com.logitics.erp.employeemilitary.mapper;

import com.logitics.erp.employeemilitary.dto.EmployeeMilitaryInfoResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMilitaryMapper {
	public List<EmployeeMilitaryInfoResponse> getEmployeeMilitaryInfo(@Param("employeeNo")String employeeNo);
}
