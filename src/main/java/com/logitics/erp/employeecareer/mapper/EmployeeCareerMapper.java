package com.logitics.erp.employeecareer.mapper;

import com.logitics.erp.employeecareer.dto.EmployeeCareerAddInfoRequest;
import com.logitics.erp.employeecareer.dto.EmployeeCareerInfoResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeCareerMapper {

	public List<EmployeeCareerInfoResponse> getEmployeeCareerInfo(@Param("employeeNo") String employeeNo);
	public List<EmployeeCareerInfoResponse> addInfo(@Param("r") EmployeeCareerAddInfoRequest addRequest);

}
