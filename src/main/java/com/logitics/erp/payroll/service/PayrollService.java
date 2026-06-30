package com.logitics.erp.payroll.service;

import com.logitics.erp.payroll.dto.PayrollRequest;
import com.logitics.erp.payroll.dto.PayrollResponse;
import com.logitics.erp.payroll.mapper.PayrollMapper;
import com.logitics.erp.payroll.repository.PayrollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final PayrollMapper payrollMapper;

    public List<PayrollResponse> getList(PayrollRequest request) {
        int todayYear = LocalDate.now().getYear();
        int todayMonth = LocalDate.now().getMonthValue();
        int yearMonth = Integer.parseInt(String.format("%02d", todayYear, todayMonth));

        return payrollMapper.getList(request, yearMonth);
    }
}
