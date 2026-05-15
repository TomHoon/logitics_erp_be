package com.logitics.erp.employeelanguage.mapper;

import com.logitics.erp.employeelanguage.dto.EmployeeLanguageInfoResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeLanguageMapper {

	public List<EmployeeLanguageInfoResponse> getEmployeeLanguageInfo(@Param("employeeNo")String employeeNo);
}
