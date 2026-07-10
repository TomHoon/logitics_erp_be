package com.logitics.erp.common.init;

import com.logitics.erp.payrollitem.entity.PayrollItemMaster;
import com.logitics.erp.payrollitem.repository.PayrollItemMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(value = 4)
public class InitPayrollItemData implements CommandLineRunner {

    private final PayrollItemMasterRepository payrollItemMasterRepository;

    @Override
    public void run(String... args) throws Exception {
        if (payrollItemMasterRepository.count() > 0) return;

        List<PayrollItemMaster> payrollItems = List.of(
                // 지급 항목
                new PayrollItemMaster("기본급", "PAY", true, true),
                new PayrollItemMaster("직책수당", "PAY", true, true),
                new PayrollItemMaster("직급수당", "PAY", true, true),
                new PayrollItemMaster("식대", "PAY", false, true),
                new PayrollItemMaster("교통비", "PAY", false, true),
                new PayrollItemMaster("통신비", "PAY", true, true),
                new PayrollItemMaster("야근수당", "PAY", true, false),
                new PayrollItemMaster("연장근로수당", "PAY", true, false),
                new PayrollItemMaster("휴일근로수당", "PAY", true, false),
                new PayrollItemMaster("성과급", "PAY", true, false),
                new PayrollItemMaster("상여금", "PAY", true, false),

                // 공제 항목
                new PayrollItemMaster("국민연금", "DEDUCTION", false, false),
                new PayrollItemMaster("건강보험", "DEDUCTION", false, false),
                new PayrollItemMaster("장기요양보험", "DEDUCTION", false, false),
                new PayrollItemMaster("고용보험", "DEDUCTION", false, false),
                new PayrollItemMaster("소득세", "DEDUCTION", false, false),
                new PayrollItemMaster("지방소득세", "DEDUCTION", false, false)
        );

        payrollItemMasterRepository.saveAll(payrollItems);
    }
}
