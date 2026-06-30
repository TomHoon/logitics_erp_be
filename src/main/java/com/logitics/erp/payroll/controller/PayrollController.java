package com.logitics.erp.payroll.controller;

import com.logitics.erp.payroll.dto.PayrollRequest;
import com.logitics.erp.payroll.dto.PayrollResponse;
import com.logitics.erp.payroll.dto.UpdateBasicSalaryRequest;
import com.logitics.erp.payroll.dto.UpdateBasicSalaryResponse;
import com.logitics.erp.payroll.service.PayrollService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payroll")
public class PayrollController {

    private final PayrollService payrollService;


    @GetMapping
    @Operation(description = "당월 등록된 사원들의 급여리스트")
    public List<PayrollResponse> getList(PayrollRequest request) {
        return payrollService.getList(request);
    }

    @Operation(description = "특정 사원의 기본급 수정")
    @PatchMapping("/{payrollId}/basic-salary")
    public UpdateBasicSalaryResponse updateBasicSalary(@PathVariable Long payrollId, @RequestBody UpdateBasicSalaryRequest request) {
        return payrollService.updateBasicSalary(payrollId, request);
    }


}
