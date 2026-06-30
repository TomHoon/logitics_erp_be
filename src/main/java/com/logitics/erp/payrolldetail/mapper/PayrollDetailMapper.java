package com.logitics.erp.payrolldetail.mapper;

import com.logitics.erp.payrolldetail.entity.PayrollDetail;

public interface PayrollDetailMapper {
    PayrollDetail findByAmountAndPayrollId(Long payrollItemMasterId, int amount);
}
