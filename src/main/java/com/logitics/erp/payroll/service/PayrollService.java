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
import com.logitics.erp.payrollitem.entity.PayrollItemMaster;
import com.logitics.erp.payrollitem.repository.PayrollItemMasterRepository;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private final PayrollItemMasterRepository payrollItemMasterRepository;

    @Value("${payroll.confirm-window-days}")
    private int confirmWindowDays;

    private static final double NATIONAL_PENSION_RATE = 0.045;
    private static final double HEALTH_INSURANCE_RATE = 0.03545;
    private static final double EMPLOYMENT_INSURANCE_RATE = 0.009;
    private static final double INCOME_TAX_RATE = 0.003;

    public List<PayrollResponse> getList(PayrollRequest request) {
        LocalDate baseDate = request.getApplyDate() != null ? request.getApplyDate() : LocalDate.now();
        int yearMonth = Integer.parseInt(String.format("%d%02d", baseDate.getYear(), baseDate.getMonthValue()));

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

    public Map<String, String> registerSalary(RegisterSalaryRequest request) {
        // 사원 ID 먼저 뽑기
        String employeeNo = request.getEmployeeNo();
        Employee emp = employeeRepository.findByEmployeeNo(employeeNo).orElse(null);

        if (emp == null) {
            throw new IllegalArgumentException("해당하는 사원 정보가 없습니다.");
        }

        Long employeeId = emp.getEmployeeId();

        request.setEmployeeId(employeeId);

        // 1. 급여지급일 (25일 || 10일) 둘 중 하나인지 확인
        String paymentDate = request.getPaymentDate();
        if (paymentDate == null || (!paymentDate.equals("10일") && !paymentDate.equals("25일"))) {
            throw new IllegalArgumentException("올바른 지급일자가 아닙니다. ex) 25일 or 10일 ");
        }

        // 2. 기본급, 직급수당, 식대, 교통비, 급여지급일 수정
        // 2.1) 당월 년월 뽑기
        int yearMonth = Integer.parseInt(String.format("%d%02d", LocalDate.now().getYear(), LocalDate.now().getMonthValue()));
        request.setPayrollYearMonth(yearMonth);

        int selectedPayementDate = paymentDate.equals("25일") ? 25 : 10;

        // 3. 당월 급여명세 없는 경우 추가
        int countOfPayrollByEmployee = payrollMapper.findPayrollCurrent(employeeId, yearMonth);
        if (countOfPayrollByEmployee <= 0) {
            // 3.1) 급여명세 데이터 추가
            Payroll newPayroll = Payroll
                    .builder()
                    .employee(emp)
                    .payrollYearMonth(yearMonth)
                    .paymentDate(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), selectedPayementDate))
                    .employeeNameSnapshot(emp.getName())
                    .departmentNameSnapshot(emp.getDepartment().getDepartmentName())
                    .positionNameSnapshot(emp.getPosition().getPositionName())
				            .totalPayAmount(BigDecimal.ZERO)
				            .totalDeductionAmount(BigDecimal.ZERO)
				            .realPayAmount(BigDecimal.ZERO)
                    .build();

            Payroll savedPayroll = payrollRepository.save(newPayroll);

            // 3.2) 기본급, 직급수당, 식대, 교통비 항목 추가
            // 3.2.1) 기본급 추가
            PayrollItemMaster basicPim = payrollItemMasterRepository.findByItemName("기본급");
            PayrollDetail basicPd = PayrollDetail
                    .builder()
                    .payroll(savedPayroll)
                    .payrollItemMaster(basicPim)
                    .itemNameSnapshot("기본급")
                    .itemTypeCodeSnapshot("PAY")
                    .amount(request.getBasicSalary())
                    .build();

            // 3.2.2) 직급수당 추가
            PayrollItemMaster responsibilityPim = payrollItemMasterRepository.findByItemName("직급수당");
            PayrollDetail responsibilityPd = PayrollDetail
                    .builder()
                    .payroll(savedPayroll)
                    .payrollItemMaster(responsibilityPim)
                    .itemNameSnapshot("직급수당")
                    .itemTypeCodeSnapshot("PAY")
                    .amount(request.getResponsibilityAllowance())
                    .build();

            // 3.2.3) 식대 추가
            PayrollItemMaster mealPim = payrollItemMasterRepository.findByItemName("식대");
            PayrollDetail mealPd = PayrollDetail
                    .builder()
                    .payroll(savedPayroll)
                    .payrollItemMaster(mealPim)
                    .itemNameSnapshot("식대")
                    .itemTypeCodeSnapshot("PAY")
                    .amount(request.getMealAllowance())
                    .build();

            // 3.2.4) 교통비 추가
            PayrollItemMaster transportationPim = payrollItemMasterRepository.findByItemName("교통비");
            PayrollDetail transportationPd = PayrollDetail
                    .builder()
                    .payroll(savedPayroll)
                    .payrollItemMaster(transportationPim)
                    .itemNameSnapshot("교통비")
                    .itemTypeCodeSnapshot("PAY")
                    .amount(request.getTransportationAllowance())
                    .build();

            payrollDetailRepository.saveAll(List.of(basicPd, responsibilityPd, mealPd, transportationPd));

            return Map.of("결과", "성공");
        }


        // 4. 이미 급여명세가 존제한 경우 Update 처리
        int result = payrollMapper.updateSalary(request);
        if (result > 0) {
            return Map.of("결과", "성공");
        }

        return Map.of("결과", "실패");
    }

    public List<PaymentPayrollResponse> getPaymentList(PaymentPayrollRequest request) {
        LocalDate baseDate = request.getApplyDate() != null ? request.getApplyDate() : LocalDate.now();
        int yearMonth = Integer.parseInt(String.format("%d%02d", baseDate.getYear(), baseDate.getMonthValue()));

        List<PaymentPayrollResponse> list = payrollMapper.getPaymentList(request, yearMonth);

        for (PaymentPayrollResponse item : list) {
            int totalPay = item.getBasicSalaryAmount()
                    + item.getMealAllowanceAmount()
                    + item.getTransportationAllowanceAmount()
                    + item.getOvertimeAllowanceAmount();

            int nationalPension = (int) Math.round(totalPay * NATIONAL_PENSION_RATE);
            int healthInsurance = (int) Math.round(totalPay * HEALTH_INSURANCE_RATE);
            int employmentInsurance = (int) Math.round(totalPay * EMPLOYMENT_INSURANCE_RATE);
            int incomeTax = (int) Math.round(totalPay * INCOME_TAX_RATE);
            int totalDeduction = nationalPension + healthInsurance + employmentInsurance + incomeTax;

            item.setTotalPayAmount(totalPay);
            item.setNationalPensionAmount(nationalPension);
            item.setHealthInsuranceAmount(healthInsurance);
            item.setEmploymentInsuranceAmount(employmentInsurance);
            item.setIncomeTaxAmount(incomeTax);
            item.setTotalDeductionAmount(totalDeduction);
            item.setRealPayAmount(totalPay - totalDeduction);

            item.setPayrollStatusText(item.getPayrollStatusCode() != null ? item.getPayrollStatusCode().getText() : null);

            String notConfirmableReason = getNotConfirmableReason(item.getPayrollStatusCode(), item.getPaymentDate());
            item.setConfirmable(notConfirmableReason == null);
            item.setNotConfirmableReason(notConfirmableReason);
        }

        return list;
    }

    private String getNotConfirmableReason(PayrollStatusCode statusCode, LocalDate paymentDate) {
        if (statusCode == PayrollStatusCode.CONFIRMED || statusCode == PayrollStatusCode.PAID) {
            return "이미 확정된 급여입니다.";
        }
        if (statusCode == PayrollStatusCode.CANCELED) {
            return "취소된 급여입니다.";
        }
        if (paymentDate == null) {
            return "지급일이 설정되지 않았습니다.";
        }

        LocalDate windowStart = paymentDate.minusDays(confirmWindowDays);
        LocalDate windowEnd = paymentDate.minusDays(1);
        LocalDate today = LocalDate.now();

        if (today.isBefore(windowStart) || today.isAfter(windowEnd)) {
            return String.format(
                    "확정 가능 기간이 아닙니다. (지급일 %d일 전인 %s부터 %s까지 확정 가능)",
                    confirmWindowDays, windowStart, windowEnd
            );
        }

        return null;
    }

    public ConfirmPayrollResponse confirmPayroll(ConfirmPayrollRequest request) {
        List<Long> payrollIds = request.getPayrollIds();
        List<Map<String, Object>> failed = new ArrayList<>();
        int confirmedCount = 0;

        for (Long payrollId : payrollIds) {
            Payroll payroll = payrollRepository.findById(payrollId).orElse(null);

            if (payroll == null) {
                failed.add(Map.of("payrollId", payrollId, "reason", "존재하지 않는 급여명세입니다."));
                continue;
            }

            String notConfirmableReason = getNotConfirmableReason(payroll.getPayrollStatusCode(), payroll.getPaymentDate());
            if (notConfirmableReason != null) {
                failed.add(Map.of("payrollId", payrollId, "reason", notConfirmableReason));
                continue;
            }

            payroll.setPayrollStatusCode(PayrollStatusCode.CONFIRMED);
            payrollRepository.save(payroll);
            confirmedCount++;
        }

        return new ConfirmPayrollResponse(confirmedCount, failed);
    }
}
