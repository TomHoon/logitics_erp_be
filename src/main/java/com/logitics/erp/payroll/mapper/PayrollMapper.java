package com.logitics.erp.payroll.mapper;

import com.logitics.erp.payroll.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface PayrollMapper {
    List<PayrollResponse> getList(PayrollRequest request, int yearMonth);

    PayrollStatusResponse getPayrollStatus();

    PayrollStatusResponse getPayrollStatusBasicInfo(int payrollYearMonth);

    Long getCompareLastMonthAmount(int payrollYearMonth, int payrollLastYearMonth);

    Long getTotalAllowance(int payrollYearMonth);

    List<EmployeeListPayrollResponse> getEmployeeListPayroll(EmployeeListPayrollRequest request);
}
