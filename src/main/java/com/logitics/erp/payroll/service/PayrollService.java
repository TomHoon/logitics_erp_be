package com.logitics.erp.payroll.service;

import com.logitics.erp.payroll.dto.PayrollRequest;
import com.logitics.erp.payroll.dto.PayrollResponse;
import com.logitics.erp.payroll.dto.UpdateBasicSalaryRequest;
import com.logitics.erp.payroll.dto.UpdateBasicSalaryResponse;
import com.logitics.erp.payroll.entity.Payroll;
import com.logitics.erp.payroll.mapper.PayrollMapper;
import com.logitics.erp.payroll.repository.PayrollRepository;
import com.logitics.erp.payrolldetail.entity.PayrollDetail;
import com.logitics.erp.payrolldetail.repository.PayrollDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final PayrollDetailRepository payrollDetailRepository;
    private final PayrollMapper payrollMapper;

    public List<PayrollResponse> getList(PayrollRequest request) {
        int todayYear = LocalDate.now().getYear();
        int todayMonth = LocalDate.now().getMonthValue();
        int yearMonth = Integer.parseInt(String.format("%02d", todayYear, todayMonth));

        return payrollMapper.getList(request, yearMonth);
    }

    public UpdateBasicSalaryResponse updateBasicSalary(Long payrollId, UpdateBasicSalaryRequest request) {

        Payroll payroll = payrollRepository.findById(payrollId).orElseThrow(() -> new IllegalArgumentException("해당 급여명세가 존재하지 않습니다."));


        PayrollDetail payrollDetail = payrollDetailRepository.findByPayroll_PayrollIdAndItemNameSnapshot(payrollId, "기본급").orElseThrow(() -> new IllegalArgumentException("해당 급여 항목이 존재하지 않습니다."));
        payrollDetail.setAmount(request.getAmount());
        payrollDetailRepository.save(payrollDetail);

        return new UpdateBasicSalaryResponse(Map.of("결과", "성공"));

    }
}
