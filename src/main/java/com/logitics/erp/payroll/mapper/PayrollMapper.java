package com.logitics.erp.payroll.mapper;

import com.logitics.erp.payroll.dto.PayrollRequest;
import com.logitics.erp.payroll.dto.PayrollResponse;

import java.util.List;

public interface PayrollMapper {
    List<PayrollResponse> getList(PayrollRequest request, int yearMonth);
}
