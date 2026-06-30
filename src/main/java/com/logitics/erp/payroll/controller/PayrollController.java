package com.logitics.erp.payroll.controller;

import com.logitics.erp.payroll.dto.PayrollRequest;
import com.logitics.erp.payroll.dto.PayrollResponse;
import com.logitics.erp.payroll.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payroll")
public class PayrollController {

    private final PayrollService payrollService;


    @GetMapping
    public List<PayrollResponse> getList(PayrollRequest request) {
        return payrollService.getList(request);
    }

}
