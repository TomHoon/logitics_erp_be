package com.logitics.erp.payroll.service;

import com.logitics.erp.department.entity.Department;
import com.logitics.erp.department.repository.DepartmentRepository;
import com.logitics.erp.employee.entity.Employee;
import com.logitics.erp.employee.repository.EmployeeRepository;
import com.logitics.erp.payroll.dto.*;
import com.logitics.erp.payroll.entity.Payroll;
import com.logitics.erp.payroll.mapper.PayrollMapper;
import com.logitics.erp.payroll.repository.PayrollRepository;
import com.logitics.erp.payrolldetail.entity.PayrollDetail;
import com.logitics.erp.payrolldetail.repository.PayrollDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final PayrollDetailRepository payrollDetailRepository;
    private final PayrollMapper payrollMapper;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

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

    public PayrollStatusResponse getPayrollStatus() {
        LocalDate nowYearMonth = LocalDate.now();
        LocalDate lastYearMonth = LocalDate.now().minusMonths(1);

        int intNowYearMonth = Integer.parseInt(String.format("%d%02d", nowYearMonth.getYear(), nowYearMonth.getMonthValue()));
        int intLastYearMonth = Integer.parseInt(String.format("%d%02d", lastYearMonth.getYear(), lastYearMonth.getMonthValue()));

        // 1. 상단 기본급 데이터 조회
        PayrollStatusResponse basicResponse = payrollMapper.getPayrollStatusBasicInfo(intNowYearMonth);

        // 2. 전월 대비 조회
        Long compareAmount = payrollMapper.getCompareLastMonthAmount(intNowYearMonth, intLastYearMonth);

        // 3. 전사원 수 조회
        int countOfEmployees = (int) employeeRepository.count();

        // 4. 총 수당조회
        Long totalAllowance = payrollMapper.getTotalAllowance(intNowYearMonth);

        // --------------- 셋팅하기 ---------------
        basicResponse.setCompareLastMonthAmount(compareAmount);
        basicResponse.setEmployeeCount(countOfEmployees);
        basicResponse.setTotalAllowanceAmount(totalAllowance);

        return basicResponse;
    }

    public List<EmployeeListPayrollResponse> getEmployeeListPayroll(EmployeeListPayrollRequest request) {

        // 1. 사원명 조회
        List<Employee> empList = employeeRepository.findByNameContaining(request.getName());

        if (request.getName() != null && empList.size() <= 0) {
            return Collections.emptyList();
        }

        // 2. 부서명 조회
        Department department = departmentRepository.findByDepartmentName(request.getDepartmentName()).orElse(null);
        if (request.getDepartmentName() != "" && request.getDepartmentName() != "전체" && request.getDepartmentName() != null && department == null) {
            return Collections.emptyList();
        }

        return payrollMapper.getEmployeeListPayroll(request);
    }
}
