package com.logitics.erp.employeecertificate.mapper;

import com.logitics.erp.employeecertificate.dto.EmployeeCertificateInfoResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeCertificateMapper {
	public List<EmployeeCertificateInfoResponse> getEmployeeCertificateInfo(@Param("employeeNo") String employeeNo);
}
